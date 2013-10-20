package jvstm.vbox

import scala.concurrent.stm.stubs.StubRef

class VBox[A](val body: VBoxBody[A]) extends StubRef[A] {
	
	def this(v0: A) = this(new VBoxBody[A](v0))
	
}