package scala.concurrent.stm.svstm


import scala.concurrent.stm._
import scala.concurrent.stm.stubs.StubRefFactory


private[svstm] trait SVSTMRefFactory extends StubRefFactory {

	override def newRef(v0: Boolean): Ref[Boolean] = new RefImpl[Boolean](v0)
	override def newRef(v0: Byte): Ref[Byte] = new RefImpl[Byte](v0)
	override def newRef(v0: Short): Ref[Short] = new RefImpl[Short](v0)
	override def newRef(v0: Char): Ref[Char] = new RefImpl[Char](v0)
	override def newRef(v0: Int): Ref[Int] = new RefImpl[Int](v0)
	override def newRef(v0: Float): Ref[Float] = new RefImpl[Float](v0)
	override def newRef(v0: Long): Ref[Long] = new RefImpl[Long](v0)
	override def newRef(v0: Double): Ref[Double] = new RefImpl[Double](v0)
	override def newRef(v0: Unit): Ref[Unit] = new RefImpl[Unit](v0)
	override def newRef[T : ClassManifest](v0: T): Ref[T] = new RefImpl[T](v0)
	
}