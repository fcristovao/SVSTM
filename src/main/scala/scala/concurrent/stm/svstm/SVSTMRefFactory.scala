package scala.concurrent.stm.jvstm

import jvstm.vbox.VBox
import scala.concurrent.stm._
import scala.concurrent.stm.stubs.StubRefFactory

private[jvstm] trait JVSTMRefFactory extends StubRefFactory {

	override def newRef(v0: Boolean): Ref[Boolean] = new VBox[Boolean](v0)
	override def newRef(v0: Byte): Ref[Byte] = new VBox[Byte](v0)
	override def newRef(v0: Short): Ref[Short] = new VBox[Short](v0)
	override def newRef(v0: Char): Ref[Char] = new VBox[Char](v0)
	override def newRef(v0: Int): Ref[Int] = new VBox[Int](v0)
	override def newRef(v0: Float): Ref[Float] = new VBox[Float](v0)
	override def newRef(v0: Long): Ref[Long] = new VBox[Long](v0)
	override def newRef(v0: Double): Ref[Double] = new VBox[Double](v0)
	override def newRef(v0: Unit): Ref[Unit] = new VBox[Unit](v0)
	override def newRef[T : ClassManifest](v0: T): Ref[T] = new VBox[T](v0)
	
}