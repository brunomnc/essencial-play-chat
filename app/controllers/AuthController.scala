package controllers

import java.lang.Object

import play.api._
import play.api.mvc._
import javax.inject._
import play.api.http.Status

@Singleton
class AuthController  @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  import services.AuthService
  import services.AuthServiceMessages._

  // TODO: Complete:
  //  - Call AuthService.login
  //     - If it's LoginSuccess, return an Ok response that sets a cookie
  //     - If it's UserNotFound or PasswordIncorrect, return a BadRequest response
  //
  // NOTE: We don't know how to create HTML yet,
  // so populate each response with a plain text message.
  def login(username: Username, password: Password): Action[AnyContent] = Action { request =>
      AuthService.login(LoginRequest(username, password)) match {
      case res: UserNotFound => Status(401)
      case res: LoginSuccess => Ok("Logged in").withCookies(
        Cookie(name = "sessionId", value = res.sessionId)
      )
      case res: PasswordIncorrect => Status(401)
      case _ => BadRequest("error")
    }
  }
}
