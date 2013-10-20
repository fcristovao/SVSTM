package svstm.transactions
import svstm.exceptions.CommitException

class TopLevelReadWriteTransaction(number: Int, parent: ReadWriteTransaction = null) extends ReadWriteTransaction(number, parent) {

	def isWriteTransaction = !boxesWritten.isEmpty
	
	def tryCommit() = {
		if(isWriteTransaction){
			Transaction.CommitLock.lock()
			try{
				if(isValidCommit()){
					val newTxNumber = Transaction.mostRecentNumber.get() + 1
					
					doCommit(newTxNumber)
					
					Transaction.mostRecentNumber.incrementAndGet()
				} else {
					throw CommitException
				}
			} finally {
				Transaction.CommitLock.unlock()
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