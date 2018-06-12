import java.io.FileInputStream

import play.api.libs.json.{JsArray, JsValue, Json}

class MessagesInConverstionCounter(val path : String) {
  val stream = new FileInputStream(path)

  def print(): Unit = {
    val json_input: JsValue = try {
      Json.parse(stream)
    } finally {
      stream.close()
    }

    println("Number of messages in conversation: " + (json_input \ "messages").asOpt[JsArray].map(_.value.size).get)
    println("")
  }
}