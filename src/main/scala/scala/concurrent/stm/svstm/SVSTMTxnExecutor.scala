package scala.concurrent.stm.svstm

import svstm.transactions.{ Transaction, TopLevelReadTransaction, TopLevelReadWriteTransaction }
import svstm.exceptions.{ WriteOnReadTransactionException, CommitException, SVSTMException }
import scala.concurrent.stm._
import scala.concurrent.stm.stubs.StubTxnExecutor
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.atomic.AtomicInteger

object SVSTMTxnExecutor {
	val commitLock = new ReentrantLock(true);

	val mostRecentNumber = new AtomicInteger(0);

	val currentTransaction = new ThreadLocal[Transaction];
}

trait SVSTMTxnExecutor extends StubTxnExecutor {
	override def apply[Z](block: InTxn => Z)(implicit mt: MaybeTxn): Z = {
		val txn =
			mt match {
				case TxnUnknown => { // no static scoped transaction exists
					SVSTMTxnExecutor.currentTransaction.get match { // try to get the current transaction dynamically
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
		SVSTMTxnExecutor.currentTransaction.set(txn);
		try {
			txn.atomic(block)
		} catch {
			case e : SVSTMException => {
				if (txn.isTopLevel) {
					val newTxn = Transaction(readOnly = false)
					execute(newTxn, block)
				} else {
					throw e
				}
			}
		} finally {
			SVSTMTxnExecutor.currentTransaction.set(txn.parent)
		}
	}
}