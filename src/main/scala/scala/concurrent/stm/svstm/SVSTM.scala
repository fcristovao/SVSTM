package scala.concurrent.stm.svstm

import scala.concurrent.stm.stubs.StubSTMImpl

class JVSTM extends StubSTMImpl with SVSTMRefFactory with SVSTMExecutor{

}