import scala.collection.immutable.ListMap

class TopNSelector(val n: Int, val p:CommentParser) {
  private val list = ListMap(p.parse().toSeq.sortWith(_._2 > _._2):_*).toList

  def print():Unit = {
    for(i <- 0 to (if(list.size>n) n else list.size)){
      println(list(i))
    }
  }

  def printLongerWords():Unit = {
    var x=0
    (0 to list.size).iterator.takeWhile(_ => x<n).foreach(i => if(list(i)._1.length>3) {println(list(i)); x=x+1} else {})
  }
}
