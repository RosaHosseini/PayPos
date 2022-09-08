package rosa.pay.sdk

import android.os.Parcelable
import rosa.pay.commontest.Mockable
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize

/**
 * A [TransactionManager] can be used to make [transactions][Transaction].
 *
 * To start a transaction, first you should create a [new transaction flow][newTransactionFlow].
 */
@Mockable
interface TransactionManager {

    /**
     * Starts a new transaction flow. To get notified about the [state][State] of the transaction
     * this flow should be collected.
     */
    fun newTransactionFlow(): Flow<Transaction>

    /**
     * Dispatches an [input][Input] into the flow based on the [state][State] of the transaction.
     * It only accepts [amount inputs][Input.Amount] if the state is [State.AWAITING_AMOUNT].
     * It only accepts [confirmation inputs][Input.Confirm] if the state is [State.AWAITING_CONFIRMATION].
     * It can always accept [cancel inputs].
     */
    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    suspend fun dispatch(input: Input)
}

/**
 * Represents a snapshot of an ongoing transaction in a transaction flow.
 */
data class Transaction(

    /**
     * The reference ID of a transaction. Only set when the transaction is completed.
     */
    val referenceId: String?,

    /**
     * The amount of a transaction, only set after an [amount input][Input.Amount] is [dispatched][TransactionManager.dispatch].
     */
    val amount: Long?,

    /**
     * The [state][State] of a transaction.
     */
    val state: State,

    /**
     * The [store][Store] in which this transaction is happening in. Only set when the state is [State.AWAITING_CONFIRMATION].
     */
    val store: Store?,

    /**
     * True when transaction is finalized, false otherwise. When a transaction is finalized then the state is either
     * [State.SUCCESS], [State.FAILURE] or [State.CANCELLED].
     */
    val isFinal: Boolean
)

/**
 * Represents a store.
 */
@Parcelize
data class Store(

    /**
     * The name of the store.
     */
    val name: String,

    /**
     * The address of the store.
     */
    val address: String,

    /**
     * The postal code of the store.
     */
    val postalCode: String
) : Parcelable

/**
 * Represents the state of a [transaction][Transaction].
 */
enum class State {

    /**
     * [TransactionManager] expects an [amount input][Input.Amount] to be [dispatched][TransactionManager.dispatch]
     */
    AWAITING_AMOUNT,

    /**
     * A transaction is being processed.
     */
    PROCESSING,

    /**
     * [TransactionManager] expects a [confirmation input][Input.Confirm] to be [dispatched][TransactionManager.dispatch]
     */
    AWAITING_CONFIRMATION,

    /**
     * The transaction is completed successfully.
     */
    SUCCESS,

    /**
     * The transaction ended with a failure.
     */
    FAILURE,

    /**
     * The transaction is cancelled by the user.
     */
    CANCELLED
}

/**
 * Represents the user inputs into a [transaction flow][Transaction].
 */
sealed class Input {

    /**
     * Represents an amount input for a transaction.
     */
    data class Amount(
        val value: Long
    ) : Input()

    /**
     * Represents a confirmation input for a transaction.
     */
    data class Confirm(
        val value: Boolean
    ) : Input()

    /**
     * Represents a cancel input for a transaction.
     */
    object Cancel : Input()
}