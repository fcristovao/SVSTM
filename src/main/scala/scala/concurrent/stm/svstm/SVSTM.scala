package scala.concurrent.stm.svstm

import scala.concurrent.stm.stubs.StubSTMImpl

class SVSTM extends StubSTMImpl with SVSTMRefFactory with SVSTMTxnExecutor with SVSTMTxnContext {
}