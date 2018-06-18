import java.io.FileInputStream
import java.text.SimpleDateFormat
import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.Implicits.{Axis, output, xyChart}
import org.sameersingh.scalaplot.XYPlotStyle
import play.api.libs.json.{JsArray, JsValue, Json}

import scala.collection.immutable.ListMap

class TimesOfMessagesAnalyzer (val path: String) {
  private var yearsMap = Map.empty[Double, Double]
  private var messagesCtr = 0

  def getDate(timestamp: Long) : Double = {
    val ts = timestamp * 1000L
    val df = new SimpleDateFormat("yyyy")
    val date = df.format(ts)
    date.toDouble
  }

  def parseSingleConversation(file : String) : Unit = {
    val stream = new FileInputStream(file)
    val json_input : JsValue = try { Json.parse(stream) } finally { stream.close() }

    var i = 0
    while (i < (json_input \ "messages").asOpt[JsArray].map(_.value.size).get) {
      val timestamp = ((json_input \ "messages")(i) \ "timestamp").as[Long]
      messagesCtr += 1
      val year = getDate(timestamp)
      if (yearsMap.contains(year)) {
        yearsMap += (year -> (yearsMap(year) + 1))
      } else {
        yearsMap += (year -> 1)
      }
      i += 1
    }

  }

  def makePlot(list : List[(Double, Double)]) : Unit = {
    output(PNG("./", "MessagesExchangedPlot"), xyChart(List(XY(list, style = XYPlotStyle.Impulses)), x = Axis(label = "Years"), y = Axis(label = "Messages"), title = "Messages exchanged plot"))
    println(output(ASCII, xyChart(List(XY(list, style = XYPlotStyle.Impulses)), x = Axis(label = "Years"), y = Axis(label = "Messages"), title = "Messages exchanged plot")))
  }

  def parse() : Unit = {
    val recursiveFileList = new RecursiveFileList(path)
    val fileList = recursiveFileList.recursiveListFiles().filter(f => f.endsWith(".json"))

    for(file <- fileList) {
      parseSingleConversation(file)
    }

    println("Messages exchanged in total: " + messagesCtr)
//    println(yearsMap)
    val list = ListMap(yearsMap.toSeq.sortWith(_._2 > _._2):_*).toList
//    println(list)
    makePlot(list)
  }
}