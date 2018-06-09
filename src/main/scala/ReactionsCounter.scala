import java.io.FileInputStream
import play.api.libs.json._

class ReactionsCounter(val path : String) {
  val stream = new FileInputStream(path)
  def print(): Unit = {
    val json_input = try {Json.parse(stream) } finally { stream.close() }
    println(json_input.toString())
  }
}
