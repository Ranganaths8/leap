package com.leap.jarstore

import com.typesafe.config.Config
import com.leap.util.Util
import java.io.{InputStream, OutputStream}
import java.net.URI
import java.util.ServiceLoader
import scala.collection.JavaConverters._

case class FilePath(path: String)

/**
 * JarStore is used to manage the upload/download of binary files,
 * like user submitted application jar.
 */
trait JarStore {
  /**
   * The scheme of the JarStore.
   * Like "hdfs" for HDFS file system, and "file" for a local
   * file system.
   */
  val scheme: String

  /**
   * Init the Jar Store.
   */
  def init(config: Config)

  /**
   * Creates the file on JarStore.
   *
   * @param fileName  name of the file to be created on JarStore.
   * @return OutputStream returns a stream into which the data can be written.
   */
  def createFile(fileName: String): OutputStream

  /**
   * Gets the InputStream to read the file
   *
   * @param fileName name of the file to be read on JarStore.
   * @return InputStream returns a stream from which the data can be read.
   */
  def getFile(fileName: String): InputStream
}

object JarStore {

  /**
   * Get a active JarStore by specifying a scheme.
   *
   * more information.
   */
  private lazy val jarstores: List[JarStore] = {
    ServiceLoader.load(classOf[JarStore]).asScala.toList
  }

  def get(rootPath: String): JarStore = {
    val scheme = new URI(Util.resolvePath(rootPath)).getScheme
    jarstores.find(_.scheme == scheme).get
  }
}