package jvstm.vbox

import scala.concurrent.stm.stubs.StubRef
import scala.concurrent.stm.InTxn

class VBox[A](var body: VBoxBody[A]) {
	
	def this(v0: A) = this(new VBoxBody[A](v0))
	
	// newValue should be of type A, but it would imply that the read and write sets were not as they are
	def commit(newValue: Any, txNumber: Int) : VBoxBody[A] = {
		val newBody = new VBoxBody[A](newValue.asInstanceOf[A], txNumber, this.body)
		this.body = newBody
		newBody
	}
}