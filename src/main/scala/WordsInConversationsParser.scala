

class WordsInConversationsParser (val path: String) extends CommentParser {

  def parse():Map[String, Int] = {
    var map = Map.empty[String, Int]

    val recursiveFileList = new RecursiveFileList(path)
    val fileList = recursiveFileList.recursiveListFiles().filter(f => f.endsWith(".json"))


    for(file <- fileList){
      val wordsInConversationParser = new WordsInConversationParser(file)
      val oneConvMap = wordsInConversationParser.parse()
      map = (map.keySet ++ oneConvMap.keySet).map {i=> (i,map.getOrElse(i,0) + oneConvMap.getOrElse(i,0))}.toMap
    }

    return map
  }
}
