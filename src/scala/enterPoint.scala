package scala

import scala.BitmapEncoder._

/**
 * Created by Moriya Shrine on 9/15/2014.
 */


object enterPoint{
  def main(args: Array[String]) {
    val url = "http://perso.wanadoo.es/larigon2/fotos/charmander.bmp"
    val imageName = "charmander.bmp"
    val imageName2 = "charmander2.bmp"
    val message = "Be sure to drink your ovaltine."
    encode(message,imageName,url)
    val output = ImageDecoder.decode(imageName2)
    println(output)
  }
}