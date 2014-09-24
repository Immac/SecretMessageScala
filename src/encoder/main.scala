package encoder

import encoder.ImageDecoder._
import encoder.ImageEncoder._

object main extends App{
  override def main(args: Array[String]) {
    //doEncode()
    doDecode()
  }
  def doEncode(): Unit ={
    val image_path = "sanae.bmp"
    val message = "Sa! Sa! Sa! Sa! Sa! Sa!"
    encode(message,image_path,image_path)
  }
  def doDecode(): Unit ={
    val url = "http://localhost/BitmapHtml/"
    val output = decode(url)
    for (item <- output)
      println(item)
  }
}