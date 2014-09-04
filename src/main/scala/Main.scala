import java.io.{FileInputStream, FileOutputStream}
import java.util.Properties

import ch.ntb.usb.USB
import dispatch.classic.XhtmlParsing._
import dispatch.classic.{Http, url}

import scala.util.{Failure, Success, Try}

object Main extends App {
  val Host = "rpsci:8080"
  val User = "RocketLauncher"
  val Password = "RocketLauncher"
  val ProjectName = "build-package"
  val UpdateTime = 30000
  val PropertiesPath = "resources/lastBuildNumber.properties"

  def getLastBuildNumber = {
    val properties = new Properties
    properties.load(new FileInputStream(PropertiesPath))
    Option(properties.getProperty("lastBuildNumber")) map (_.toInt) getOrElse 0
  }

  def setLastBuildNumber(buildNumber: Int) = {
    val properties = new Properties
    properties.setProperty("lastBuildNumber", buildNumber.toString)
    properties.store(new FileOutputStream(PropertiesPath), "Update last build number")
  }

  // Connect to the Turret
  implicit val device = USB.getDevice(0x2123, 0x1010) // Vendor ID, Product ID
  device.open(1, 0, -1)

//  Lakshmi()
//  Fire()

  try {
    val resource = url(s"http://$Host/job/$ProjectName/lastFailedBuild/api/xml") as_!(User, Password)
    while (true) {
      Http(resource </> { nodes =>
        val buildNumber = (nodes \ "number").head.text.toInt
        if (buildNumber != getLastBuildNumber) {
          Try {
            (nodes \ "changeSet" \ "item" \ "author" \ "fullName").head.text match {
              case "jawc" => James()
              case "mesfin.mebrate" => Mesfin()
              case "joan.goyeau" => Joan()
              case "david.granger" => David()
              case "karl.parsons" => Karl()
              case "ian.white" => Ian()
              case "romiro.calle" => Romiro()
              case name => throw new MatchError(s"$name broke the build but his position doesn't match")
            }
          } match {
            case Success(_) => for (_ <- 1 to 4) Fire()
            case Failure(e) => println(e.getMessage)
          }

          setLastBuildNumber(buildNumber)
        }
      })

      synchronized(wait(UpdateTime))
    }
  } finally device.close()
}
