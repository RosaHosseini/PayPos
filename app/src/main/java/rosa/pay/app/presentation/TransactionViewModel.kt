package rosa.pay.app.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import rosa.pay.core.AppDispatchers
import rosa.pay.sdk.Input
import rosa.pay.sdk.State
import rosa.pay.sdk.Transaction
import rosa.pay.sdk.TransactionManager
import rosa.pay.app.mapper.toUiTransactionState
import rosa.pay.app.model.InvalidAmountException
import rosa.pay.app.model.InvalidStoreException
import rosa.pay.app.model.Money
import rosa.pay.app.model.TransactionUiState
import rosa.pay.app.navigation.PayNavigator
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionManager: TransactionManager,
    private val appDispatchers: AppDispatchers,
    private val navigator: PayNavigator
) : ViewModel() {

    private val _transactionUiState = MutableStateFlow<TransactionUiState>(getDefaultTransaction())
    val transactionUiState: StateFlow<TransactionUiState> = _transactionUiState

    private var transactionJob: Job? = null

    init {
        reloadData()
    }

    fun isTransactionAmountPermitted(money: Money): Boolean = money.amount > 5

    private fun reloadData() {
        transactionJob?.cancel()

        transactionJob = viewModelScope.launch(appDispatchers.default) {
            transactionManager.newTransactionFlow().catch {
                if (it is IllegalStateException)
                    Log.d(TAG, it.message.toString())
                else throw it
            }.collect { transaction ->
                if (transaction.state == State.CANCELLED) onReset()
                else try {
                    transaction.toUiTransactionState()?.also {
                        _transactionUiState.value = it
                        navigator.navigateTo(it.destination)
                    }
                } catch (e: InvalidStoreException) {
                    onError(e, transaction)
                } catch (e: InvalidAmountException) {
                    onError(e, transaction)
                }
            }
        }
    }

    fun onConfirmTransaction(isConfirmed: Boolean) {
        dispatch(Input.Confirm(isConfirmed))
    }

    fun onSubmitAmount(transferredMoney: Money) {
        dispatch(Input.Amount(transferredMoney.amount))
    }

    fun onReset() {
        reloadData()
    }

    private fun onError(e: Exception, transaction: Transaction? = null) {
        Log.d(TAG, e.message.toString())
        if (transaction?.isFinal != true) {
            dispatch(Input.Cancel)
        }
    }

    private fun dispatch(input: Input) = viewModelScope.launch(appDispatchers.io) {
        try {
            transactionManager.dispatch(input)
        } catch (ignored: IllegalStateException) {
            Log.d(TAG, ignored.message.toString())
        } catch (ignored: IllegalArgumentException) {
            Log.d(TAG, ignored.message.toString())
        }
    }

    private fun getDefaultTransaction() = TransactionUiState.Processing

    companion object {
        private const val TAG = "TRANSACTION"
    }
}