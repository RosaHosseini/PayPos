package rosa.pay.transaction.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rosa.pay.core.AppDispatchers
import rosa.pay.navigation.PayNavigator
import rosa.pay.sdk.Input
import rosa.pay.sdk.State
import rosa.pay.sdk.Transaction
import rosa.pay.sdk.TransactionManager
import rosa.pay.transaction.mapper.toUiTransactionState
import rosa.pay.transaction.model.InvalidAmountException
import rosa.pay.transaction.model.InvalidStoreException
import rosa.pay.transaction.model.Money
import rosa.pay.transaction.model.TransactionUiState

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionManager: TransactionManager,
    private val appDispatchers: AppDispatchers,
    private val navigator: PayNavigator
) : ViewModel() {

    private val _transactionUiState = MutableStateFlow<TransactionUiState>(getDefaultTransaction())
    val transactionUiState: StateFlow<TransactionUiState> = _transactionUiState

    private var transactionJob: Job? = null
    private val transactionHandler = CoroutineExceptionHandler { _, e ->
        when (e) {
            is IllegalStateException,
            is IllegalArgumentException ->
                Log.d(TAG, e.message.toString())
            else -> throw e
        }
    }

    init {
        reloadData()
    }

    fun isTransactionAmountPermitted(money: Money): Boolean = money.amount > 5

    private fun reloadData() {
        transactionJob?.cancel()
        transactionJob = viewModelScope.launch(appDispatchers.default + transactionHandler) {
            transactionManager.newTransactionFlow().collect { transaction ->
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

    private fun dispatch(input: Input) {
        viewModelScope.launch(appDispatchers.io + transactionHandler) {
            transactionManager.dispatch(input)
        }
    }

    private fun getDefaultTransaction() = TransactionUiState.Processing

    companion object {
        private const val TAG = "TRANSACTION"
    }
}