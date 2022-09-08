package rosa.pay.app.mapper

import rosa.pay.sdk.State
import rosa.pay.sdk.Store
import rosa.pay.sdk.Transaction
import rosa.pay.app.model.InvalidAmountException
import rosa.pay.app.model.InvalidStoreException
import rosa.pay.app.model.Money
import rosa.pay.app.model.TransactionUiState

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