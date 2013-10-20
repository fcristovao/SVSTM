package scala.concurrent.stm.svstm

import svstm.transactions.{Transaction, TopLevelReadTransaction, TopLevelReadWriteTransaction}
import svstm.exceptions.{WriteOnReadException, CommitException}
import scala.concurrent.stm._
import scala.concurrent.stm.stubs.StubTxnExecutor

trait SVSTMExecutor extends StubTxnExecutor {
	
	override def apply[Z](block: InTxn => Z)(implicit mt: MaybeTxn): Z = { 
		mt match {
			case TxnUnknown => { // no transaction exists yet, so build one
				Transaction(readOnly = true).atomic(block)
			}
			case txn: Transaction => {
				txn.makeNestedTransaction().atomic(block)
			}
		}
	}
}