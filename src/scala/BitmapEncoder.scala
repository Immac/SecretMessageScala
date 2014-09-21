package scalaimport scala.collection.mutable.ArrayBuffer/** * Created by Moriya Shrine on 9/15/2014. */object BitmapEncoder extends App {  def toBinary(i: Int, digits: Int = 8) =    String.format("%" + digits + "s", i.toBinaryString).replace(' ', '0')  def toBitArray(chars: Array[Char]):String = {    val arrayOfCharArray =  for (char <- chars) yield toBinary(char.toInt)    var output:String = ""    for (array <- arrayOfCharArray){      output += array.toString    }    output  }  def toBitArray(input: String):String = {    val charArray = input.toCharArray    val output = toBitArray(charArray)    output  }  def encodeMessage(bytes: Array[Byte], binaryString: String, startByte: Int ):Array[Byte] = {    var index:Int = 0    var currentByte:Int = 0    var output: ArrayBuffer[Byte] = ArrayBuffer[Byte]()    for (byte <- bytes) {      val outByte = if(index < binaryString.length && currentByte > startByte)((byte & ~1) | binaryString(index).toString.toInt).toByte else byte      output += outByte      println(outByte)      currentByte +=1      index += (if(currentByte > startByte) 1 else 0)    }    output.toArray  }  def encode(message:String,imageName:String,url: String = "") =  {    import java.nio.file.{Files, Paths}    import scala.OtherUtilities._    val bitmapExtension = ".bmp"    val startByte = 64    val lastChar = 255.asInstanceOf[Char]    val outputMessage = message + lastChar    val imagePath = AppendExtension(imageName,bitmapExtension)    val encodedImageName = AppendBeforeExtension(imageName,bitmapExtension,"2")    if(!url.isEmpty) ResourceFinder.ImageFromUrl(url,imagePath)    val byteArray: Array[Byte] = Files.readAllBytes(Paths.get("" + imagePath))    val messageAsBitArray = toBitArray(message)    val size:Int = byteArray.length - (startByte + 1)    val needed:Int = messageAsBitArray.length    if (size < needed) throw new Exception("Not Enough Space In The Picture")    val output = encodeMessage(byteArray,messageAsBitArray,startByte)    Files.write(Paths.get("" + encodedImageName),output)  }}object ResourceFinder extends App{  import java.io.File  import java.net.URL  import scala.OtherUtilities.AppendExtension  import scala.sys.process._  def ImageFromUrl(url:String,outputPath:String) =  {    val bitmapExtension = ".bmp"    val newOutputPath = AppendExtension(outputPath, bitmapExtension)    new URL(url) #> new File(newOutputPath) !!  }}object OtherUtilities extends App{  def AppendExtension(input: String, extension: String): String = {    if (input.endsWith(extension)) input else input + extension  }  def AppendBeforeExtension(input:String,extension: String,appended: String): String = {    var output = if (input.endsWith(extension)) input.take(input.lastIndexOf(".")) else input    output += appended + extension    output  }  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {    val p = new java.io.PrintWriter(f)    try { op(p) } finally { p.close() }  }}