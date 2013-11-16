package svstm.transactions

import scala.concurrent.stm.InTxn
import scala.concurrent.stm.stubs.StubInTxn
import scala.concurrent.stm.svstm.SVSTMTxnExecutor

import svstm.vbox.VBox

object Transaction {
	def apply(readOnly: Boolean = false, parent: Transaction = null): Transaction = {
		(parent, readOnly) match {
			case (null, true) => new TopLevelReadTransaction(SVSTMTxnExecutor.mostRecentNumber.get())
			case (null, false) => new TopLevelReadWriteTransaction(SVSTMTxnExecutor.mostRecentNumber.get())
			case (txn, _) => txn.makeNestedTransaction()
		}
	}
}

abstract class Transaction(val number: Int, val parent: Transaction = null) extends InTxn with StubInTxn {
	def this(parent: Transaction) = this(parent.number, parent)

	def getBoxValue[T](vbox: VBox[T]): T

	def setBoxValue[T](vbox: VBox[T], value: T)

	def doCommit()

	def makeNestedTransaction(): Transaction

	def hasParent() = parent != null;
	
	def isTopLevel() = !hasParent()

	def atomic[A](block: InTxn => A): A = {
		val result = block(this)
		this.doCommit()
		result
	}

}