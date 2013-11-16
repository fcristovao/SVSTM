package svstm.transactions

import svstm.vbox.VBox
import svstm.exceptions.{WriteOnReadException, CommitException}
import scala.concurrent.stm.InTxn
import scala.concurrent.stm.stubs.StubInTxn
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.stm.svstm.SVSTMTxnExecutor


object Transaction{
	def apply(readOnly: Boolean = false, parent: Transaction = null) : Transaction = {
		if(readOnly){
			new TopLevelReadTransaction(SVSTMTxnExecutor.mostRecentNumber.get())
		} else {
			new TopLevelReadWriteTransaction(SVSTMTxnExecutor.mostRecentNumber.get())
		}
	}
}

abstract class Transaction(val number: Int, val parent: Transaction = null) extends InTxn with StubInTxn{

	def this(parent: Transaction) = this(parent.number, parent)

	def getBoxValue[T](vbox: VBox[T]) : T

	def setBoxValue[T](vbox: VBox[T], value: T)
    
  def doCommit()
	
	def makeNestedTransaction(): Transaction
	
	//override def retry(): Nothing
	
	def atomic[A](block: InTxn => A): A = {
		val result = block(this)
		this.doCommit()
		result
	}
	
}