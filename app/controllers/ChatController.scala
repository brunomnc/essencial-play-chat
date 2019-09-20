package controllers

import javax.inject.Inject
import play.api._
import play.api.mvc._
import services.ChatServiceMessages

class ChatController @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  // TODO: Complete:
  //  - Check if the user is logged in
  //     - If they are, return an Ok response containing a list of messages
  //     - If they aren't, redirect to the login page
  //
  // NOTE: We don't know how to create HTML yet,
  // so populate each response with a plain text message.
  def index: Action[AnyContent] = Action { request =>
    isAuth(request) { credentials => Ok(ChatService.messages.mkString("\n"))  }
  }

  def isAuth(request: Request[AnyContent]) (func: Credentials => Result): Result =
    request.cookies.get("sessionId") match {
      case Some(cookie)  => AuthService.whoami(cookie.value) match {
        case res: Credentials => func(res)
        case res: SessionNotFound => Status(401)(res.sessionId + " not found")
      }
      case None => BadRequest("User not logged in")
  }


  // TODO: Complete:
  //  - Check if the user is logged in
  //     - If they are, create a message from the relevant author
  //     - If they aren't, redirect to the login page
  //
  // NOTE: We don't know how to create HTML yet,
  // so populate each response with a plain text message.
  def submitMessage(text: String): Action[AnyContent] = Action { request =>
    isAuth(request) {
      credentials => ChatService.chat(credentials.username, text)
      Redirect(routes.ChatController.index())
    }
  }
}
