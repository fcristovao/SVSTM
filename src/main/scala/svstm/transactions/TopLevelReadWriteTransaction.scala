package svstm.transactions
import svstm.exceptions.CommitException
import scala.concurrent.stm.svstm.SVSTMTxnExecutor

class TopLevelReadWriteTransaction(number: Int, parent: ReadWriteTransaction = null) extends ReadWriteTransaction(number, parent) {

	def isWriteTransaction = !boxesWritten.isEmpty
	
	def tryCommit() = {
		if(isWriteTransaction){
			SVSTMTxnExecutor.commitLock.lock()
			try{
				if(isValidCommit()){
					val newTxNumber = SVSTMTxnExecutor.mostRecentNumber.get() + 1
					
					doCommit(newTxNumber)
					
					SVSTMTxnExecutor.mostRecentNumber.incrementAndGet()
				} else {
					throw CommitException
				}
			} finally {
				SVSTMTxnExecutor.commitLock.unlock()
			}
		}
	}
	
	def isValidCommit() : Boolean = {
		for((vBox, vBoxBody) <- bodiesRead; if vBox.body != vBoxBody) {
			return false
		}
		true
	}
	
	def doCommit(newTxNumber: Int) = {
		for((vBox, newValue) <- boxesWritten)
			yield vBox.commit(newValue, newTxNumber)
	}
	
}