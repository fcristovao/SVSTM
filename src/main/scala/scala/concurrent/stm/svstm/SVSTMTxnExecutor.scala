package scala.concurrent.stm.svstm

import svstm.transactions.{ Transaction, TopLevelReadTransaction, TopLevelReadWriteTransaction }
import svstm.exceptions.{ WriteOnReadException, CommitException }
import scala.concurrent.stm._
import scala.concurrent.stm.stubs.StubTxnExecutor
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.atomic.AtomicInteger

object SVSTMTxnExecutor {
	val commitLock = new ReentrantLock(true);

	val mostRecentNumber = new AtomicInteger(0);

	val currentTransaction = new SVSTMTxnContext();

	def isInTransaction() = currentTransaction.dynCurrentOrNull != null;
}

trait SVSTMTxnExecutor extends StubTxnExecutor {
	override def apply[Z](block: InTxn => Z)(implicit mt: MaybeTxn): Z = {
		val txn =
			mt match {
				case TxnUnknown => { // no static scoped transaction exists
					SVSTMTxnExecutor.currentTransaction.dynCurrentOrNull match {
						case null => Transaction(readOnly = true);
						case txn: Transaction => txn.makeNestedTransaction()
					}
				}
				case txn: Transaction => {
					txn.makeNestedTransaction()
				}
			}
		execute(txn, block);
	}

	protected def execute[Z](txn: Transaction, block: InTxn => Z): Z = {
		try {
			SVSTMTxnExecutor.currentTransaction.set(txn);
			val result = txn.atomic(block)
			SVSTMTxnExecutor.currentTransaction.set(txn.parent)
			result
		} catch {
			case WriteOnReadException | CommitException => {
				val newTxn = Transaction(readOnly = false, parent = txn.parent)
				execute(newTxn, block)
			}
		}
	}
}