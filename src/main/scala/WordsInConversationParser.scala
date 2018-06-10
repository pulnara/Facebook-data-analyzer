import java.io.FileInputStream

import play.api.libs.json._

class WordsInConversationParser(val path : String) extends CommentParser{
  val stream = new FileInputStream(path)

  def parse():Map[String, Int] = {
    val json_input : JsValue = try { Json.parse(stream) } finally { stream.close() }

    var i = 0
    var map = Map.empty[String, Int]

    while (i < (json_input \ "messages").asOpt[JsArray].map(_.value.size).get) {
      val comment = ((json_input \ "messages")(i) \ "content").validate[String]
      comment match{
        case s: JsSuccess[String] =>
                                     map = s.get
                                            .toLowerCase
                                            .split("\\W+")
                                            .foldLeft(map){
                                              (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
                                            }

        case e: JsError => Unit
      }
      i=i+1
    }

    return map
  }
}
