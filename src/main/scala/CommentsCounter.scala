import java.io.FileInputStream
import play.api.libs.json._
import scala.collection.immutable.ListMap

class CommentsCounter(val path : String) {
  private val stream = new FileInputStream(path)

  def printGroups(commentsNumber : Int, json_input : JsValue) : Unit = {
    var commentsInGrps  = 0
    var groups: Map[String, Int] = Map()
    var i = 0
    while (i < commentsNumber) {
      val group = (((json_input \ "comments")(i) \ "data")(0) \ "comment" \ "group").validate[String]
      group match {
        case s: JsSuccess[String] => {
          commentsInGrps += 1
          val name = group.get.toString
          if (groups.contains(name)) {
            groups += (name -> (groups(name) + 1))
          } else {
            groups += (name -> 1)
          }
        }
        case e: JsError => Unit
      }
      i += 1
    }
    val list = ListMap(groups.toSeq.sortWith(_._2 > _._2):_*).toList

    println("Including " + commentsInGrps + " comments in groups.")
    println()
    println("Most active in groups:")
    for (i <- 0 to Math.min(list.size, 10)) println(list(i)._1 + ": " + list(i)._2)
  }

  def print() : Unit = {
    val json_input : JsValue = try { Json.parse(stream) } finally { stream.close() }
    val commentsNumber = (json_input \ "comments").asOpt[JsArray].map(_.value.size).get
    println("Number of comments written: " + commentsNumber)
    printGroups(commentsNumber, json_input)
  }

}
