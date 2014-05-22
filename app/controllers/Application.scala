package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee.{Iteratee, Concurrent, Enumerator}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def printer = WebSocket.using[JsValue] { request =>
    //Concurrent.broadcast returns (Enumerator, Concurrent.Channel)
    val (out,channel) = Concurrent.broadcast[JsValue]

    //log the message to stdout and send response back to client
    val in = Iteratee.foreach[JsValue] {
      msg => println(msg)
             (msg \ "time").asOpt[Long] match {
               case Some(t) => if(t % 10 == 0) channel push(msg)
             } 
    }
    (in,out)
  }
}
