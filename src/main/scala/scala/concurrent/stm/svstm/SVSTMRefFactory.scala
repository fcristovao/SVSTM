package scala.concurrent.stm.svstm

import scala.concurrent.stm._
import scala.concurrent.stm.stubs.StubRefFactory

private[svstm] trait SVSTMRefFactory extends StubRefFactory {
	override def newRef(v0: Boolean): Ref[Boolean] = new SVSTMRef[Boolean](v0)
	override def newRef(v0: Byte): Ref[Byte] = new SVSTMRef[Byte](v0)
	override def newRef(v0: Short): Ref[Short] = new SVSTMRef[Short](v0)
	override def newRef(v0: Char): Ref[Char] = new SVSTMRef[Char](v0)
	override def newRef(v0: Int): Ref[Int] = new SVSTMRef[Int](v0)
	override def newRef(v0: Float): Ref[Float] = new SVSTMRef[Float](v0)
	override def newRef(v0: Long): Ref[Long] = new SVSTMRef[Long](v0)
	override def newRef(v0: Double): Ref[Double] = new SVSTMRef[Double](v0)
	override def newRef(v0: Unit): Ref[Unit] = new SVSTMRef[Unit](v0)
	override def newRef[T : ClassManifest](v0: T): Ref[T] = new SVSTMRef[T](v0)
}