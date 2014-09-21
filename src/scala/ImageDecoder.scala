package scala

import scala.collection.mutable.ArrayBuffer


/**
 * Created by Moriya Shrine on 9/16/2014.
 */
import passera.unsigned._
object ImageDecoder extends App {
  def decode(path:String): String ={
    import java.nio.file.{Paths, Files}

    val byteSkip:Int = 64
    val byteArray: Array[Byte] = Files.readAllBytes(Paths.get("" + path))
    val uIntArray:Array[UInt] = for(byte <- byteArray) yield (byte.toInt.toUInt)
    //var binaryStringsB:ArrayBuffer[Char] = ArrayBuffer[Char]()
    var binaryString:String = ""
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
             byteArrayB+= (Integer.parseInt(binaryString,2)).toUInt
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

}
