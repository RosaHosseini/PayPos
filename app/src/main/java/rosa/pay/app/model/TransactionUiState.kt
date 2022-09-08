package rosa.pay.app.model

import android.os.Parcelable
import rosa.pay.navigation.NavigationDestination
import rosa.pay.sdk.Store
import rosa.pay.app.navigation.AwaitingAmountDestination
import rosa.pay.app.navigation.AwaitingConfirmationDestination
import rosa.pay.app.navigation.LoadingDestination
import rosa.pay.app.navigation.TransactionFailureDestination
import rosa.pay.app.navigation.TransactionSucceedDestination
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