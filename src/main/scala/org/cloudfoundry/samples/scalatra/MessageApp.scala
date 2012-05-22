package org.cloudfoundry.samples.scalatra
import scala.xml._
import org.scalatra._
import com.mongodb.casbah.Imports._
import com.mongodb._
import org.cloudfoundry.runtime.env.CloudEnvironment
import org.cloudfoundry.runtime.env.MongoServiceInfo
import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._

class MessageApp extends ScalatraServlet {
  def logger = LoggerFactory.getLogger("MessageApp")
  val cloudEnvironment = new CloudEnvironment()
  val mongoServices = cloudEnvironment.getServiceInfos(classOf[MongoServiceInfo])
  val bulletinBoard: MongoDB = mongoServices.asScala.toList match {
  	case head :: _ => {
  	  val db = MongoConnection(head.getHost(), head.getPort())(head.getDatabase())
  	  db.authenticate(mongoServices.get(0).getUserName(),mongoServices.get(0).getPassword())
  	  db
  	 }
    case _ => {
      MongoConnection()("bulletin_board")
     }
  }
  val coll = bulletinBoard("msgs")

  get("/") {
    redirect("/msgs")
  }

  get("/msgs") {
    <body>
      <h1>Messages</h1>
      <ul>
        {
          for (l <- coll) yield <li>From: { l.getOrElse("author", "???") } - { l.getOrElse("msg", "???") }</li>
        }
      </ul>
      <h1>Post a Message</h1>
      <form method="POST" action="/msgs">
        Author: <input type="text" name="author"/>
        Message: <input type="text" name="msg"/>
        <input type="submit"/>
      </form>
    </body>
  }

  post("/msgs") {
    val builder = MongoDBObject.newBuilder
    builder += "author" -> params("author")
    builder += "msg" -> params("msg")

    coll += builder.result.asDBObject
    redirect("/msgs")
  }
  
}