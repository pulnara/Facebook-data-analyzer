import java.io.FileInputStream
import play.api.libs.json._

class ReactionsCounter(val path : String) {
  private val stream = new FileInputStream(path)

  def print(): Unit = {
    val json_input : JsValue = try { Json.parse(stream) } finally { stream.close() }

    var i = 0
    var like_ctr = 0
    var super_ctr = 0
    var haha_ctr = 0
    var sad_ctr = 0
    var angry_ctr = 0
    var wow_ctr = 0
    val size = (json_input \ "reactions").asOpt[JsArray].map(_.value.size).get

    while (i < size) {
      val tmp = ((json_input \ "reactions")(i) \ "data")(0) \ "reaction" \ "reaction"
      tmp.as[String] match {
        case "LIKE" => like_ctr += 1
        case "LOVE" => super_ctr += 1
        case "HAHA" => haha_ctr += 1
        case "SORRY" => sad_ctr += 1
        case "ANGER" => angry_ctr += 1
        case "WOW" => wow_ctr += 1
        case _ => {}
      }
      i += 1
    }

    println("Total reactions number: " + size)
    println("Likes: " + like_ctr)
    println("Loves: " + super_ctr)
    println("Haha: " + haha_ctr)
    println("Sad: " + sad_ctr)
    println("Angry: " + angry_ctr)
    println("Wow: " + wow_ctr)
    println("")
  }
}
