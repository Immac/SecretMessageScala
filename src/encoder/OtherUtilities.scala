package encoder
object OtherUtilities extends App{
  def AppendExtension(input: String, extension: String): String = {
    if (input.endsWith(extension)) input else input + extension
  }
  def AppendBeforeExtension(input:String,extension: String,appended: String): String = {
    var output = if (input.endsWith(extension)) input.take(input.lastIndexOf(".")) else input
    output += appended + extension
    output
  }
  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
}