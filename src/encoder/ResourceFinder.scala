package encoder
/**
 * Created by Moriya Shrine on 9/23/2014.
 */

object ResourceFinder extends App{
  import java.io.File
  import java.net.URL
  import encoder.OtherUtilities.AppendExtension
  import scala.sys.process._
  def ImageFromUrl(url:String,outputPath:String):String =
  {
    val bitmapExtension = ".bmp"
    val newOutputPath = AppendExtension(outputPath, bitmapExtension)
    new URL(url) #> new File(newOutputPath) !!
  }
}
