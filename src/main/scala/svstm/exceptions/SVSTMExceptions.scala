package svstm.exceptions

sealed trait SVSTMException extends Error

/**
 * An instance of <code>WriteOnReadException</code> is thrown by a
 * thread whenever a write attempt is made to a VBox within a
 * ReadOnlyTransaction.
 *
 * An application should never catch instances of this class, as the
 * purpose of throwing an instance of this class is to make a
 * non-local exit from the currently running transaction, and restart
 * it with a new type of transaction that is able to deal with writes.
 * This is done by the SVSTM runtime and should not be masked by the
 * application code in anyway.
 *
 * The class <code>WriteOnReadException</code> is specifically a
 * subclass of <code>Error</code> rather than <code>Exception</code>,
 * even though it is a "normal occurrence", because many applications
 * catch all occurrences of <code>Exception</code> and then discard
 * the exception.
 *
 */
case object WriteOnReadTransactionException extends SVSTMException
case object CommitException extends SVSTMException