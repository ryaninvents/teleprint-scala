package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee.{Iteratee, Concurrent, Enumerator}
import play.api.libs.concurrent.Execution.Implicits._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def printer = WebSocket.using[String] { request =>
    //Concurrent.broadcast returns (Enumerator, Concurrent.Channel)
    val (out,channel) = Concurrent.broadcast[String]

    //log the message to stdout and send response back to client
    val in = Iteratee.foreach[String] {
      msg => println(msg)
             //the Enumerator returned by Concurrent.broadcast subscribes to the channel and will 
             //receive the pushed messages
             channel push("RESPONSE: " + msg)
    }
    (in,out)
  }
}
