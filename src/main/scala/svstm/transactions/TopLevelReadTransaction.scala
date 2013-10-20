package svstm.transactions

class TopLevelReadTransaction(number: Int, parent: ReadTransaction = null) extends ReadTransaction(number, parent) {

}