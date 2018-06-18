import java.io.FileInputStream
import java.text.SimpleDateFormat
import org.sameersingh.scalaplot.Implicits._
import play.api.libs.json.{JsArray, JsValue, Json}
import java.util.Calendar

class FriendsNumberAnalyzer(val path : String) {
  val stream = new FileInputStream(path)

  def getDate(timestamp: Long) : Double = {
    val ts = timestamp * 1000L
    val df = new SimpleDateFormat("yyyy")
    val date = df.format(ts)
    date.toDouble
  }

  def print() : Unit  = {
    var years : Map[Double, Double] = Map()
    val json_input : JsValue = try { Json.parse(stream) } finally { stream.close() }
    for (i <- 0 until  (json_input \ "friends").asOpt[JsArray].map(_.value.length).get) {
      val year = getDate(((json_input \ "friends")(i) \ "timestamp").as[Long])
      if (years.contains(year)) {
        years += (year -> (years(year) + 1))
      } else {
        years += (year -> 1)
      }
    }
    val now = Calendar.getInstance()
    val currentYear = now.get(Calendar.YEAR)
    println("You've made " + years.getOrElse(currentYear, 0).toString.dropRight(2) + " friends so far this year.")

    output(PNG("./", "FriendshipsMakingPlot"), xyChart(List(XY(years.toSeq)), x = Axis(label = "Years"), y = Axis(label = "New friends"), title = "Friendships making plot"))
    println(output(ASCII, xyChart(List(XY(years.toSeq)), x = Axis(label = "Years"), y = Axis(label = "New friends"), title = "Friendships making plot")))
  }
}
