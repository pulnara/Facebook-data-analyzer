object Application {
  def main(args: Array[String]): Unit = {
    println("Kanapka!!!")
  }
  val reactionsCounter = new ReactionsCounter("/home/aga/Pobrane/FBData/likes_and_reactions/posts_and_comments.json")
  reactionsCounter.print()
}
