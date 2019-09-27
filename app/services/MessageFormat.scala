package services
import play.api.libs.json._
import ChatServiceMessages.Message

trait MessageFormat {
  implicit val authorWrites: Writes[Message] = (o: Message) => JsString(o.author)
  implicit val textWrites: Writes[Message] = (o: Message) => JsString(o.text)
}
