package scala.concurrent.stm.svstm

import scala.concurrent.stm.InTxn
import scala.concurrent.stm.Ref
import scala.concurrent.stm.stubs.StubRef

import svstm.transactions.Transaction
import svstm.vbox.VBox

class SVSTMRef[A](v0: A) extends VBox[A](v0) with StubRef[A] {
	class ViewImpl[A](override val ref: Ref[A]) extends Ref.View[A] with StubRef.StubView[A] {
	}

	private def impl(implicit txn: InTxn): Transaction = txn.asInstanceOf[Transaction]

	override def get(implicit txn: InTxn): A = impl.getBoxValue(this)
	override def set(v: A)(implicit txn: InTxn) = impl.setBoxValue(this, v)

	override def single: Ref.View[A] = new ViewImpl(this)
}