class MessagesInConversationsParser (val path: String) extends CommentParser {

  def parse():Map[String, Int] = {
    var map = Map.empty[String, Int]

    val recursiveFileList = new RecursiveFileList(path)
    val fileList = recursiveFileList.recursiveListFiles().filter(f => f.endsWith(".json"))

    for(file <- fileList){
      val messagesInConversationCounter = new MessagesInConversationCounter(file)
      val value = messagesInConversationCounter.count()
      val os = System.getProperty("os.name")
      val array = if (os == "Linux") {
        file.split("/")
      } else {
        file.split("\\\\")
      }
      val name = array(array.length-2).split("_")
      val key = name(0)
      map += (key -> value)
    }

    return map
  }
}
