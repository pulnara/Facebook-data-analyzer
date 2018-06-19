import java.io.FileInputStream
import java.text.SimpleDateFormat

import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.XYPlotStyle
import play.api.libs.json._

import scala.collection.immutable.ListMap

class LocationsParser (val path: String){
  val stream = new FileInputStream(path)

  def getDate(timestamp: Long) : Double = {
    val ts = timestamp * 1000L
    val df = new SimpleDateFormat("yyyy")
    val date = df.format(ts)
    date.toDouble
  }

  def parse():Map[Long, (Double, Double)] = {
    var map = Map.empty[Long, (Double, Double)]
    var i = 0
    var j = 0
    var k = 0

    val json_input: JsValue = try {
      Json.parse(stream)
    } finally {
      stream.close()
    }

    while (i < (json_input \ "location_history").asOpt[JsArray].map(_.value.size).get) {
      while(j < (((json_input \ "location_history")(i) \ "attachments" )(0) \ "data" ).asOpt[JsArray].map(_.value.size).get) {

        val latitude = ((((json_input \ "location_history") (i) \ "attachments") (0) \ "data") (j) \ "place" \ "coordinate" \ "latitude").asOpt[Double].get
        val longitude = ((((json_input \ "location_history") (i) \ "attachments") (0) \ "data") (j) \ "place" \ "coordinate" \ "longitude").asOpt[Double].get
        val timestamp = ((((json_input \ "location_history") (i) \ "attachments") (0) \ "data") (j) \ "place" \ "creation_timestamp").asOpt[Long].get

        map += (timestamp -> (latitude, longitude))

        j=j+1
      }
      i=i+1
    }

    return map
  }

  def print(): Unit ={
    var places : Map[Long, (Double, Double)] = this.parse()

    places = ListMap(places.toSeq.sortBy(_._1):_*)

    output(PNG("./", "LocationsPlot"), xyChart(XY(places.values.toSeq, style = XYPlotStyle.Points), x = Axis(label = "X"), y = Axis(label = "Y"), title = "Locations plot"))
    println(output(ASCII, xyChart(XY(places.values.toSeq, style = XYPlotStyle.Points), x = Axis(label = "X"), y = Axis(label = "Y"), title = "Locations plot")))

  }

}
