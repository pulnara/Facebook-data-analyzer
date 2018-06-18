import scala.collection.immutable.ListMap
import Console._

class TopNSelector(val n: Int, val p:CommentParser) {
  private val list = ListMap(p.parse().toSeq.sortWith(_._2 > _._2):_*).toList

  def print():Unit = {
    println(s"${BLACK}Top words in conversation:$WHITE")
    for(i <- 0 to (if(list.size>n) n else list.size-1)){
      println(list(i)._1 + ": " + list(i)._2)
    }
    println("")
  }

  def printLongerWords():Unit = {
    var x=0
    println(s"${BLACK}Top words in conversation with more than 3 letters:$WHITE")
    (0 to list.size).iterator.takeWhile(_ => x<n).foreach(i => if(list(i)._1.length>3) {println(list(i)._1 + ": " +  list(i)._2); x=x+1} else {})
    println("")
  }

  def printAll():Unit = {
    for(i <- list.indices){
      println(list(i))
    }
    println("")
  }
}
