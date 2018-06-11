import java.io.File
import java.security.InvalidParameterException

object Application {
  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("Proper argument: <PATH> to your FB data's directory.")
      System.exit(1)
    }
//    println(args{0})
    val path = args{0}
    val file = new File(path)
    try {
      if (file.exists() && file.isDirectory) {
        val reactionsCounter = new ReactionsCounter(file + "/likes_and_reactions/posts_and_comments.json")
        reactionsCounter.print()
        val wordsInConversationParser = new WordsInConversationParser(file + "/messages/agnieszkapulnar_2f35bd08c5/message.json")
        val topNSelector = new TopNSelector(10, wordsInConversationParser)
        topNSelector.print()
        topNSelector.printLongerWords()
      }
      else {
        throw new InvalidParameterException("Invalid path given.")
      }
    } catch {
      case e => println(e.getMessage)
    }

    ///home/aga/Pobrane/FBData/likes_and_reactions/posts_and_comments.json
    //"D:/WIEiT/Scala/facebook-dane/likes_and_reactions/posts_and_comments.json"
    // "D:/WIEiT/Scala/facebook-dane/messages/AgnieszkaPulnar_ca96c8a69a/message.json"

  }
}
