package svstm.transactions

import svstm.exceptions.WriteOnReadTransactionException
import svstm.vbox.VBox

class ReadTransaction(number: Int, parent: ReadTransaction = null) extends Transaction(number, parent) {

	def this(parent: ReadTransaction) = this(parent.number, parent)
	
  def getBoxValue[T](vbox: VBox[T]): T =  vbox.body.getBody(number).value 

  def setBoxValue[T](vbox: VBox[T], value: T): Unit = throw WriteOnReadTransactionException

  def doCommit(): Unit = {
  	//nothing to do
  }
  
  def makeNestedTransaction: Transaction = {
  	new ReadTransaction(this)
  }

}