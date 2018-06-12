import java.io.FileInputStream
import java.security.InvalidParameterException

import play.api.libs.json._


class ReactionsCounter(val path : String) {
  val stream = new FileInputStream(path)

  def print(): Unit = {
    val json_input : JsValue = try { Json.parse(stream) } finally { stream.close() }

    var i = 0
    var like_ctr = 0
    var super_ctr = 0
    var haha_ctr = 0
    var sad_ctr = 0
    var angry_ctr = 0
    var wow_ctr = 0
    while (i < (json_input \ "reactions").asOpt[JsArray].map(_.value.size).get) {
      val tmp = ((json_input \ "reactions")(i) \ "data")(0) \ "reaction" \ "reaction"
      tmp.as[String] match {
        case "LIKE" => like_ctr += 1
        case "LOVE" => super_ctr += 1
        case "HAHA" => haha_ctr += 1
        case "SORRY" => sad_ctr += 1
        case "ANGER" => angry_ctr += 1
        case "WOW" => wow_ctr += 1
        case _ => throw new InvalidParameterException("Invalid type of reaction.")
      }
      i += 1
    }
    println("Number of likes: " + like_ctr)
    println("Number of loves: " + super_ctr)
    println("Number of haha: " + haha_ctr)
    println("Number of sad: " + sad_ctr)
    println("Number of angry: " + angry_ctr)
    println("Number of wow: " + wow_ctr)
    println("")
  }
}
