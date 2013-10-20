package jvstm.vbox

class VBoxBody[+A](val value: A, val version: Int,  val next: VBoxBody[A]) {
	
	def this(v0: A) = this(v0, 0, null)
	
	def getBody(maxVersion: Int) : VBoxBody[A] = 
		if (version > maxVersion)
			next.getBody(maxVersion)
		else
			this
	
}