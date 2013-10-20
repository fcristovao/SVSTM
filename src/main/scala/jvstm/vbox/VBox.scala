package jvstm.vbox

import scala.concurrent.stm.Ref
import scala.concurrent.stm.InTxn
import scala.concurrent.stm.Ref.View

import jvstm.exceptions.NotImplemented

class VBox[A](val body: VBoxBody[A]) extends Ref[A] {
	
	def this(v0: A) = this(new VBoxBody[A](v0))
	
	//from SourceLike[A]:
	def get(implicit txn: InTxn): A = throw NotImplemented
  def getWith[Z](f: (A) => Z)(implicit txn: InTxn): Z = throw NotImplemented
  def relaxedGet(equiv: (A, A) => Boolean)(implicit txn: InTxn): A = throw NotImplemented 
  
  //from SinkLike[A]
  def set(v: A)(implicit txn: InTxn) = throw NotImplemented
  def trySet(v: A)(implicit txn: InTxn): Boolean = throw NotImplemented

  //from RefLike[A]
  def swap(v: A)(implicit txn: InTxn): A = throw NotImplemented
  def transform(f: A => A)(implicit txn: InTxn) = throw NotImplemented
  def transformIfDefined(pf: PartialFunction[A,A])(implicit txn: InTxn): Boolean = throw NotImplemented
  
  //from Ref[A]
  def single: View[A] = throw NotImplemented
}