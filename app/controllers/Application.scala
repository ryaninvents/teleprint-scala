package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee.{Iteratee, Concurrent, Enumerator}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import io.muller.printerface._

object Application extends Controller {

  var outputChannels:List[Concurrent.Channel[JsValue]] = Nil
  var printerOption:Option[SerialPrinter] = None

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def broadcast(msg:JsValue):Unit = outputChannels foreach {ch => ch push(msg)}

  def broadcast(msg:Map[String,String]):Unit = broadcast(Json.toJson(msg))

  def processPrinterCmd(cmd:String, msg:JsValue, channel:Concurrent.Channel[JsValue]):Unit = printerOption match {
    case Some(printer) => cmd match {
      case "home" => printer send Command.Home
      case _ => channel push Json.toJson(Map("error" -> "Unrecognized command", "cmd" -> cmd))
    }
    case None => channel push Json.toJson(Map("error" -> "Not connected to a printer"))
  }

  def printerCmd = WebSocket.using[JsValue] { request =>
    //Concurrent.broadcast returns (Enumerator, Concurrent.Channel)
    val (out,channel) = Concurrent.broadcast[JsValue]
    var counter:Int = 0

    def processMessage(msg:JsValue):Unit = (msg \ "cmd").asOpt[String] match {
      case Some(cmd) => cmd match {
        case "connect" => {
          (msg \ "port").asOpt[String] match {
            case Some(port) => {
              val baud = (msg \ "baud").asOpt[Int] match {
                case Some(b) => b
                case None => 115200
              }
              val printer = new SerialPrinter(port, baud)
              printerOption = Some(printer)
              printer.connect()
              channel push Json.toJson(Map("cmd" -> Json.toJson("connectionStatus"), "connected" -> Json.toJson(true), "port" -> Json.toJson(port), "baud" -> Json.toJson(baud)))
            }
            case None => channel push Json.toJson(Map("error" -> "Must specify a port to connect to"))
          }
        }
        case _ => processPrinterCmd(cmd, msg, channel)
      }
      case None => channel push Json.toJson(Map("error" -> "No \"cmd\" specified"))
    }

    //log the message to stdout and send response back to client
    val in = Iteratee.foreach[JsValue] { event =>
      processMessage(event)
    }
    (in,out)
  }
}
