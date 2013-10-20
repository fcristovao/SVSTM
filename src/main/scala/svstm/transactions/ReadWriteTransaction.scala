package svstm.transactions

import scala.collection.mutable.Map
import scala.collection.immutable.List
import svstm.vbox.VBox
import svstm.vbox.VBoxBody

abstract class ReadWriteTransaction(number: Int, parent: ReadWriteTransaction = null) extends Transaction(number, parent) {

	var boxesWritten = Map[VBox[_],Any]()
	var bodiesRead = List[(VBox[_],VBoxBody[_])]()
	
	protected def getLocalValue[T](vbox: VBox[T]): Option[T] = {
		boxesWritten.get(vbox).asInstanceOf[Option[T]] match {
			case None if parent != null => parent.getLocalValue(vbox)
			case x => x
		}
	}
	
  def getBoxValue[T](vbox: VBox[T]): T =  {
  	getLocalValue(vbox) match {
  		case None => {
  			val body = vbox.body.getBody(number)
  			bodiesRead = (vbox, body) +: bodiesRead
  			body.value
  		}
  		case Some(x) => x
  	}
  }

  def setBoxValue[T](vbox: VBox[T], value: T): Unit = {
  	boxesWritten += (vbox -> value)
  }

  def doCommit(): Unit = {
  	tryCommit()
  }
  
  def tryCommit() : Unit

  def makeNestedTransaction: Transaction = {
  	new NestedReadWriteTransaction(this)
  }
  
}