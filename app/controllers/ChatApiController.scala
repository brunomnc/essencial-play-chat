package controllers

import java.util.UUID

import play.api._
import play.api.mvc._
import javax.inject._
import play.api.libs.json.JsValue

@Singleton
class ChatApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  // TODO: Complete:
  //  - Extract the sessionId from the Authorization header
  //  - Pass it to AuthService.whoami to check the session:
  //     - If the session was found, return an Ok response containing the messages
  //     - If the session was not found, return an Unauthorized response
  def messages: Action[JsValue] = Action(parse.json) { implicit request =>
    val res = for {
      author <- (request.body \ "author").validate[String].asEither.fold(_ => Left("author error"), author => Right(author))
      text <- (request.body \ "text").validate[String].asEither.fold(_ => Left("text error"), text => Right(text))
      result <- ChatService.sendMessage(author, text)
    } yield result

    res.fold(e => BadRequest(e), _ => Ok("message sent"))
  }

  // TODO: Complete:
  //  - Extract the sessionId from the Authorization header
  //  - Pass it to AuthService.whoami to check the session:
  //     - If the session was found:
  //        - Extract a ChatRequest from the request JSON
  //           - If it was valid, pass it to ChatService.chat
  //           - If it was invalud, return a BadRequest response
  //     - If the session was not found, return an Unauthorized response
  def chat = Action { implicit request =>
    ???
  }
}
