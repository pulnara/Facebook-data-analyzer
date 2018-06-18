import java.io.File
import java.security.InvalidParameterException
import Console._

object Application {
  def getPersonsMessagesPath(path : String, name : String) : String = {
    val file = new File(path + "/messages/")
    val result = file.listFiles().filter(_.isDirectory).map(_.getPath.toLowerCase).toList.filter(f => f.contains(name.toLowerCase))
      if (result.nonEmpty) {
        result.head + "/message.json"
      } else {
        throw new InvalidParameterException("Invalid person name.")
      }
  }

  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("Proper argument: <PATH> to your FB data's directory.")
      System.exit(1)
    }
    println(s"${BLUE}Your Facebook Data Analysis:\n$WHITE")
    val path = args{0}
    val file = new File(path)
    try {
      if (file.exists() && file.isDirectory) {
        val reactionsCounter = new ReactionsCounter(file + "/likes_and_reactions/posts_and_comments.json")
        println(s"${MAGENTA}Reactions:$WHITE")
        reactionsCounter.print()

//        println("Type name of person:")
//        val who = scala.io.StdIn.readLine().replaceAll(" ", "")
        val who = "AgnieszkaPulnar"
        println(s"${GREEN}For " + who + s":$WHITE")
        val messagesPath = getPersonsMessagesPath(path, who)
        val wordsInConversationParser = new WordsInConversationParser(messagesPath)
        val topNSelector = new TopNSelector(10, wordsInConversationParser)
        topNSelector.print()
        topNSelector.printLongerWords()

        val messagesInConversationCounter = new MessagesInConversationCounter(messagesPath)
        messagesInConversationCounter.print()

        val messagesInConversationsParser = new MessagesInConversationsParser(file + "/messages")
        val topNSelector2 = new TopNSelector(10, messagesInConversationsParser)
        println(s"${CYAN}Top messages in conversations:$WHITE")
        topNSelector2.print()

        val commentsCounter = new CommentsCounter(file + "/comments/comments.json")
        println(s"${YELLOW}Comments analysis:$WHITE")
        commentsCounter.print()

//        val friendsNumberAnalyzer = new FriendsNumberAnalyzer(file + "/friends/friends.json")
//        println(s"\n${MAGENTA}Friendship making analysis:$WHITE")
//        friendsNumberAnalyzer.print()

        val locationParser = new LocationsParser(file + "/location_history/your_location_history.json")
        //print(locationParser.parse())
        locationParser.print()

      }
      else {
        throw new InvalidParameterException("Invalid path given.")
      }
    } catch {
      case e: InvalidParameterException => println(e.getMessage)
    }
  }
}
