package encoder

import java.net.URL
import java.nio.file.{Files, Paths}
import org.htmlcleaner.HtmlCleaner
import scala.collection.mutable.{ArrayBuffer, ListBuffer}


object ImageDecoder extends App {
  def getImageSources(url: String):List[String] = {
    var sources = new ListBuffer[String]
    val cleaner = new HtmlCleaner
    val rootNode = cleaner.clean(new URL(url))
    val elements = rootNode.getElementsByName("img", true)
    for (element <- elements) {
      val imageUrl = element.getAttributeByName("src")
      sources += imageUrl
    }
    sources.toList
  }

  def getSecretMessage(path: String): String = {
    import passera.unsigned._
    val byteSkip:Int = 64
    val byteArray: Array[Byte] = Files.readAllBytes(Paths.get("" + path))
    val uIntArray:Array[UInt] = for(byte <- byteArray) yield byte.toInt.toUInt
    var binaryString = ""
    var byteArrayB:ArrayBuffer[UInt] = ArrayBuffer[UInt]()
    var counter:Int = 0
    var readCount:Int = 0
    for(byte <- uIntArray){
      counter += 1
      if (counter > byteSkip){
        val bit:UInt = math.abs(byte.toInt%2).toUInt
        binaryString += bit.toString
        readCount += 1
        if (readCount % 8 == 0) {
          byteArrayB+= Integer.parseInt(binaryString, 2).toUInt
          val value = Integer.parseInt(binaryString,2)
          if(value >= 128 || value == 0) {
            val returnValue = (for (x <- byteArrayB) yield x.toChar).mkString("").trim
            return returnValue
          }
          binaryString = ""
        }
      }
    }
    byteArrayB.toString()
  }

  def decode(url:String): List[String] ={
    import encoder.OtherUtilities._
    var output = new ListBuffer[String]
    val extension = ".bmp"
    val imageSources = getImageSources(url)
    val path: String = "image"
    var imgCounter = 1

    for(imgSrc <- imageSources)
    {
      val outputPath = AppendExtension(path + imgCounter.toString, extension)
      ResourceFinder.ImageFromUrl(imgSrc,outputPath)
      output += getSecretMessage(outputPath)
      imgCounter += 1
    }

    output.toList
  }
}
