package services

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.api.mvc.Result

object ChatService {
  import ChatServiceMessages._

  private var postedMessages = Vector[Message]()

  // TODO: Complete:
  //  - Delete all messages in `postedMessages`
  def clear(): Unit = {
    postedMessages = Vector.empty[Message]
  }

  // TODO: Complete:
  //  - Return a list of messages in `postedMessages`
  def messages: Seq[Message] = {
    postedMessages.toList
  }

  // TODO: Complete:
  //  - Add a new message to `postedMessages`
  def chat(author: String, text: String): Message = {
    val message = Message(author, text)
    postedMessages = postedMessages :+ message
    message
  }

  def sendMessage(author: String, text: String): Either[String, Unit] = {
    if(messages.exists(m => m.author == author && m.text == text)){
      Left("Message already exists")
    }
    val message = Message(author, text)
    postedMessages = postedMessages :+ message
    Right(())
  }
}
