package controllers
import play.api.mvc._
import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc.Results.{BadRequest, Ok}
import services.AuthServiceMessages

@Singleton
class AuthApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  import services.AuthService
  import services.AuthServiceMessages._

  // TODO: Complete:
  //  - Extract the request JSON and parse it as a LoginRequest
  //  - Pass the LoginRequest to AuthService
  //  - Match on the return value and return an appropriate response:
  //     - If the login was successful, return an Ok result containing the response
  //     - If the user was not found, return a BadRequest result containing the response
  //     - If the password was incorrect, return a PasswordIncorrect result containing the response
  def login: Action[JsValue] = Action(parse.json) { implicit request =>
    val res = for {
      username <- (request.body \ "username").validate[String].asEither.fold(_ => Left("username validation error"), user => Right(user))
      password <- (request.body \ "password").validate[String].asEither.fold(_ => Left("invalid password"), password => Right((password)))
      loginResult <- AuthService.apilogin(LoginRequest(username, password))
    } yield loginResult

    res.fold(e => BadRequest(""), s => Ok(s.toString))
  }

  // TODO: Complete:
  //  - Extract the sessionId from the Authorization header
  //  - Grab the session from AuthService.whoami
  //  - Return an appropriate result based on the response:
  //     - If the session was found, return an Ok response
  //     - If the session was not found, return a NotFound response
  def whoami = Action { implicit request =>
    ???
  }
}
