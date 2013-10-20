package scala.concurrent.stm.svstm

import svstm.transactions.{Transaction, TopLevelReadTransaction, TopLevelReadWriteTransaction}
import svstm.exceptions.{WriteOnReadException, CommitException}
import scala.concurrent.stm._
import scala.concurrent.stm.stubs.StubTxnExecutor

trait SVSTMExecutor extends StubTxnExecutor {
	
	override def apply[Z](block: InTxn => Z)(implicit mt: MaybeTxn): Z = { 
		mt match {
			case TxnUnknown => { // no transaction exists yet, so build one
				try {
					Transaction(readOnly = true).atomic(block)
				}
				catch {
					case WriteOnReadException | CommitException => Transaction().atomic(block)
							// The "Transaction().atomic(block)" is an error, because we could be in an nested transaction and a CommitException would make it a top-level one
				}
			}
			case txn: Transaction => {
				txn.makeNestedTransaction().atomic(block)
			}
		}
	}
}