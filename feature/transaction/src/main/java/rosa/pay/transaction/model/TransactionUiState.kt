package rosa.pay.transaction.model

import android.os.Parcelable
import rosa.pay.navigation.NavigationDestination
import rosa.pay.sdk.Store
import rosa.pay.transaction.navigation.AwaitingAmountDestination
import rosa.pay.transaction.navigation.AwaitingConfirmationDestination
import rosa.pay.transaction.navigation.LoadingDestination
import rosa.pay.transaction.navigation.TransactionFailureDestination
import rosa.pay.transaction.navigation.TransactionSucceedDestination
import kotlinx.parcelize.Parcelize

sealed class TransactionUiState(val destination: NavigationDestination) {
    object AwaitingAmount : TransactionUiState(AwaitingAmountDestination)

    object Processing : TransactionUiState(LoadingDestination)

    @Parcelize
    data class Success(val transactionAmount: Money) :
        TransactionUiState(TransactionSucceedDestination), Parcelable

    @Parcelize
    data class Failure(val transactionAmount: Money) :
        TransactionUiState(TransactionFailureDestination), Parcelable

    @Parcelize
    data class AwaitingConfirmation(val store: Store, val transactionAmount: Money) :
        TransactionUiState(AwaitingConfirmationDestination), Parcelable
}