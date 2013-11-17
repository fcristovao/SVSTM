package scala.concurrent.stm.svstm

import svstm.transactions.Transaction
import scala.concurrent.stm.InTxn
import scala.concurrent.stm.MaybeTxn
import scala.concurrent.stm.TxnUnknown
import scala.concurrent.stm.impl.TxnContext

trait SVSTMTxnContext extends TxnContext {
	override def findCurrent(implicit mt: MaybeTxn): Option[InTxn] = {
		mt match {
			case TxnUnknown => {
				dynCurrentOrNull match {
					case null => None
					case txn => Some(txn)
				}
			}
			case txn: Transaction => Some(txn)
		}
	}
	
  override def dynCurrentOrNull: InTxn = SVSTMTxnExecutor.currentTransaction.get()
}