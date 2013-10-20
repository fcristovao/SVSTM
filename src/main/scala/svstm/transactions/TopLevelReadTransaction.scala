package jvstm.transactions

class TopLevelReadTransaction(number: Int, parent: ReadWriteTransaction = null) extends ReadTransaction(number, parent) {

}