object Application {
  def main(args: Array[String]): Unit = {
//    println("Kanapka!!!")
    val reactionsCounter = new ReactionsCounter("D:/WIEiT/Scala/facebook-dane/likes_and_reactions/posts_and_comments.json")
    reactionsCounter.print()
    val wordsInConversationParser = new WordsInConversationParser("D:/WIEiT/Scala/facebook-dane/messages/AgnieszkaPulnar_ca96c8a69a/message.json")
    val topNSelector = new TopNSelector(10, wordsInConversationParser)
    topNSelector.print()
    topNSelector.printLongerWords()

    val wordsInConversationsParser = new WordsInConversationsParser("D:/WIEiT/Scala/facebook-dane/messages/")
    wordsInConversationsParser.parse()
    val topNSelector2 = new TopNSelector(10,  wordsInConversationsParser)
    topNSelector2.print()
    topNSelector2.printLongerWords()
    ///home/aga/Pobrane/FBData/likes_and_reactions/posts_and_comments.json

  }
}
