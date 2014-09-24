package encoder
import java.nio.file.{Files, Paths}
import encoder.OtherUtilities._

  object ImageEncoder extends App {

    def toBinary(i: Int, digits: Int = 8) = {
      String.format("%" + digits + "s", i.toBinaryString).replace(' ', '0')
    }
    def toBitArray(chars: Array[Char]):String = {
      val arrayOfCharArray =  for (char <- chars) yield toBinary(char.toInt)
      var output:String = ""
      for (array <- arrayOfCharArray){
        output += array.toString
      }
      output
    }

    def toBitArray(input: String):String = {
      val charArray = input.toCharArray
      val output = toBitArray(charArray)
      output
    }

    def encodeMessage(bytes: Array[Byte], binaryString: String, startByte: Int ):Array[Byte] = {
      import scala.collection.mutable.ArrayBuffer
      var index:Int = 0
      var currentByte:Int = 0
      var output: ArrayBuffer[Byte] = ArrayBuffer[Byte]()
      for (byte <- bytes) {
        val outByte = if(index < binaryString.length && currentByte > startByte)((byte & ~1) | binaryString(index).toString.toInt).toByte else byte
        output += outByte
        println(outByte)
        currentByte +=1
        index += (if(currentByte > startByte) 1 else 0)
      }
      output.toArray
    }


    def encode(message:String,imageName:String,path: String = "") = {

      val bitmapExtension = ".bmp"
      val startByte = 64
      val lastChar = 255.asInstanceOf[Char]
      val encodedImageName = AppendBeforeExtension(imageName,bitmapExtension,"2")
      val byteArray: Array[Byte] = Files.readAllBytes(Paths.get("" + path))
      val messageAsBitArray = toBitArray(message)
      val size:Int = byteArray.length - (startByte + 1)
      val needed:Int = messageAsBitArray.length
      if (size < needed) throw new Exception("Not Enough Space In The Picture")
      val output = encodeMessage(byteArray,messageAsBitArray,startByte)
      Files.write(Paths.get("" + encodedImageName),output)
    }
  }
