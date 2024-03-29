package services

object AuthService {
  import services.AuthServiceMessages._

  private val passwords = Map[Username, Password](
    "alice"   -> "password1",
      "bob"     -> "password2",
      "charlie" -> "password3"
  )

  private var sessions = Map[SessionId, Username]()

  // TODO: Complete:
  //  - Check if the username is in `passwords`
  //     - If it is, check the password:
  //        - If it's correct, create a `session` and return a `LoginSuccess`
  //        - If it isn't, return `PasswordIncorrect`
  //     - If it isn't, return `UserNotFound`
  def login(request: LoginRequest): LoginResponse = {
    passwords.get(request.username) match {
      case None => UserNotFound(request.username)
      case Some(password) => {
        if(password == request.password) {
          val sessionId = genSessionId
          sessions += sessionId -> request.username
          LoginSuccess(sessionId)
        } else PasswordIncorrect(request.username)
      }
    }
  }

  def apilogin(request: LoginRequest): Either[LoginResponse, SessionId] = {
    passwords.get(request.username) match {
      case None => Left(UserNotFound(request.username))
      case Some(password) => {
        if(password == request.password) {
          val sessionId = genSessionId
          sessions += sessionId -> request.username
          Right(sessionId)
        } else Left(PasswordIncorrect(request.username))
      }
    }
  }

  def genSessionId: String = java.util.UUID.randomUUID toString

  // TODO: Complete:
  //  - Check if the session if in `sessions`:
  //     - If it is, delete it
  //     - If it isn't, do nothing
  def logout(sessionId: SessionId): Unit = {
    if(sessions.exists(s => s._1 == sessionId)) sessions -= sessionId
  }

  // TODO: Complete:
  //  - Check if the session is in `sessions`:
  //     - If it is, return `Credentials`
  //     - If it isn't, return `SessionNotFound`
  def whoami(sessionId: SessionId): WhoamiResponse = {
    if(sessions.exists(s => s._1 == sessionId))
      Credentials(sessionId, sessions(sessionId))
    else
      SessionNotFound(sessionId)
  }
}
