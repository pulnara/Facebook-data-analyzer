import java.io.File

class RecursiveFileList(val path: String) {

  def recursiveListFiles(dir: String = path): List[String] = {
    val file = new File(dir)

    file.listFiles.filter(_.isFile)
      .map(_.getPath).toList ++
      file.listFiles.filter(_.isDirectory)
        .map(_.getPath).map(recursiveListFiles(_)).toList.flatten
  }
}
