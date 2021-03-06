package svstm.transactions
import scala.concurrent.stm.InTxn

class NestedReadWriteTransaction(number: Int, parent: ReadWriteTransaction = null) extends ReadWriteTransaction(number, parent){
	def this(parent: ReadWriteTransaction) = this(parent.number, parent)

	this.bodiesRead = parent.bodiesRead
	
	def tryCommit() = {
		parent.bodiesRead = this.bodiesRead;
		if (parent.boxesWritten.isEmpty) {
		    parent.boxesWritten = boxesWritten;
		} else {
		    parent.boxesWritten ++= boxesWritten;
		}
	}
}