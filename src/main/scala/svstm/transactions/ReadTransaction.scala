package jvstm.transactions

import jvstm.vbox.VBox
import jvstm.exceptions.WriteOnReadException

class ReadTransaction(number: Int, parent: Transaction = null) extends Transaction(number, parent) {

  def getBoxValue[T](vbox: VBox[T]): T =  vbox.body.getBody(number).value 

  def setBoxValue[T](vbox: VBox[T], value: T): Unit = throw WriteOnReadException

  def doCommit(): Unit = {
  	//nothing to do
  }

}