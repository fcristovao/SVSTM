package jvstm.transactions

import jvstm.vbox.VBox
import scala.concurrent.stm.InTxn
import scala.concurrent.stm.stubs.StubInTxnEnd
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.atomic.AtomicInteger

object Transaction{
	val CommitLock = new ReentrantLock(true);
	
	val mostRecentNumber = new AtomicInteger(0);
}


abstract class Transaction(val number: Int, val parent: Transaction = null) extends InTxn with StubInTxnEnd{

	def this(parent: Transaction) = this(parent.number, parent)

	def getBoxValue[T](vbox: VBox[T]) : T

	def setBoxValue[T](vbox: VBox[T], value: T)
    
  def doCommit()
}