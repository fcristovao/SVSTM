package jvstm.transactions

import jvstm.vbox.VBox

abstract class Transaction(val number: Int, val parent: Transaction = null) {

	def this(parent: Transaction) = this(parent.number, parent)

	def getBoxValue[T](vbox: VBox[T]) : T

	def setBoxValue[T](vbox: VBox[T], value: T)
    
  def doCommit()
}