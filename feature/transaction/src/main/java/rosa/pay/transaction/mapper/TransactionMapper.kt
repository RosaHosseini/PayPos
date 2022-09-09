package rosa.pay.transaction.mapper

import rosa.pay.sdk.State
import rosa.pay.sdk.Store
import rosa.pay.sdk.Transaction
import rosa.pay.transaction.model.InvalidAmountException
import rosa.pay.transaction.model.InvalidStoreException
import rosa.pay.transaction.model.Money
import rosa.pay.transaction.model.TransactionUiState

@Throws(InvalidAmountException::class, InvalidStoreException::class)
fun Transaction.toUiTransactionState(): TransactionUiState? {
    return when (state) {
        State.AWAITING_AMOUNT -> TransactionUiState.AwaitingAmount
        State.PROCESSING -> TransactionUiState.Processing
        State.FAILURE -> TransactionUiState.Failure(Money(checkAmountNull(amount)))
        State.CANCELLED -> null
        State.SUCCESS -> TransactionUiState.Success(Money(checkAmountNull(amount)))
        State.AWAITING_CONFIRMATION -> TransactionUiState.AwaitingConfirmation(
            checkStoreNotNull(store),
            Money(checkAmountNull(amount))
        )
    }
}

private fun checkAmountNull(amount: Long?): Long {
    return amount ?: throw InvalidAmountException
}

private fun checkStoreNotNull(store: Store?): Store {
    return store ?: throw InvalidStoreException
}