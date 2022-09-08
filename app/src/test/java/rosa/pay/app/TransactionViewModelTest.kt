package rosa.pay.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import rosa.pay.commontest.CoroutineTestRule
import rosa.pay.commontest.coroutineTestCase
import rosa.pay.commontest.testDispatchers
import rosa.pay.sdk.Input
import rosa.pay.sdk.State
import rosa.pay.sdk.Store
import rosa.pay.sdk.Transaction
import rosa.pay.sdk.TransactionManager
import rosa.pay.app.model.Money
import rosa.pay.app.model.TransactionUiState
import rosa.pay.app.navigation.AwaitingAmountDestination
import rosa.pay.app.navigation.AwaitingConfirmationDestination
import rosa.pay.app.navigation.LoadingDestination
import rosa.pay.app.navigation.PayNavigator
import rosa.pay.app.navigation.TransactionFailureDestination
import rosa.pay.app.navigation.TransactionSucceedDestination
import rosa.pay.app.presentation.TransactionViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class TransactionViewModelTest {
    private val navigator: PayNavigator = mock()
    private val transactionManager: TransactionManager = mock()
    private val viewModel: TransactionViewModel by lazy {
        TransactionViewModel(
            transactionManager,
            testDispatchers,
            navigator
        )
    }

    private fun mockedTransaction(
        state: State,
        amount: Long? = 1,
        store: Store? = mockedStore
    ) = Transaction(
        referenceId = null,
        amount,
        state,
        store,
        isFinal = false
    )

    private val mockedStore: Store
        get() = Store(
            name = "name",
            address = "address",
            postalCode = "postal-code"
        )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `onConfirmTransaction would dispatch Confirm in transactionManager`() = coroutineTestCase {
        val isConfirmed = true
        whenever {
            viewModel.onConfirmTransaction(isConfirmed)
        }
        then {
            verify(transactionManager).dispatch(Input.Confirm(isConfirmed))
        }
    }

    @Test
    fun `onSubmitAmount would dispatch Amount in transactionManager`() = coroutineTestCase {
        val mockedAmount = 100L
        whenever {
            viewModel.onSubmitAmount(Money(mockedAmount))
        }
        then {
            verify(transactionManager).dispatch(Input.Amount(mockedAmount))
        }
    }

    @Test
    fun `onLoadData would navigate to LoadingDestination when receive PROCESSING`() =
        coroutineTestCase {
            given {
                whenever(transactionManager.newTransactionFlow()) doReturn flowOf(
                    mockedTransaction(State.PROCESSING)
                )
            }
            whenever {
                viewModel // init viewModel
            }
            then {
                verify(transactionManager, only()).newTransactionFlow()
                assertEquals(viewModel.transactionUiState.value, TransactionUiState.Processing)
                verify(navigator, only()).navigateTo(LoadingDestination)
            }
        }

    @Test
    fun `onLoadData would navigateTo TransactionFailureDestination when receive FAILURE`() =
        coroutineTestCase {
            given {
                whenever(transactionManager.newTransactionFlow()) doReturn flowOf(
                    mockedTransaction(
                        State.FAILURE
                    )
                )
            }
            whenever {
                viewModel // init viewModel
            }
            then {
                verify(transactionManager, only()).newTransactionFlow()
                assert(viewModel.transactionUiState.value is TransactionUiState.Failure)
                verify(navigator, only()).navigateTo(TransactionFailureDestination)
            }
        }

    @Test
    fun `onLoadData would navigateTo TransactionSucceedDestination when receive SUCCESS`() =
        coroutineTestCase {
            given {
                whenever(transactionManager.newTransactionFlow()) doReturn flowOf(
                    mockedTransaction(
                        State.SUCCESS
                    )
                )
            }
            whenever {
                viewModel // init viewModel
            }
            then {
                verify(transactionManager, only()).newTransactionFlow()
                assert(viewModel.transactionUiState.value is TransactionUiState.Success)
                verify(navigator, only()).navigateTo(TransactionSucceedDestination)
            }
        }

    @Test
    fun `onLoadData would navigateTo AwaitingAmountDestination when receive AWAITING_AMOUNT`() =
        coroutineTestCase {
            given {
                whenever(transactionManager.newTransactionFlow()) doReturn flowOf(
                    mockedTransaction(
                        State.AWAITING_AMOUNT
                    )
                )
            }
            whenever {
                viewModel // init viewModel
            }
            then {
                verify(transactionManager, only()).newTransactionFlow()
                assert(viewModel.transactionUiState.value is TransactionUiState.AwaitingAmount)
                verify(navigator, only()).navigateTo(AwaitingAmountDestination)
            }
        }

    @Test
    fun `onLoadData navigateTo AwaitingConfirmationDestination when get AWAITING_CONFIRMATION`() =
        coroutineTestCase {
            given {
                whenever(transactionManager.newTransactionFlow()) doReturn flowOf(
                    mockedTransaction(
                        State.AWAITING_CONFIRMATION
                    )
                )
            }
            whenever {
                viewModel // init viewModel
            }
            then {
                verify(transactionManager, only()).newTransactionFlow()
                assert(
                    viewModel.transactionUiState.value is TransactionUiState.AwaitingConfirmation
                )
                verify(navigator, only()).navigateTo(AwaitingConfirmationDestination)
            }
        }

    @Test
    fun `onLoadData would reset TransactionFlow when receive CANCELLED`() =
        coroutineTestCase {
            given {
                whenever(transactionManager.newTransactionFlow())
                    .thenReturn(flowOf(mockedTransaction(State.CANCELLED)))
                    .thenReturn(flowOf(mockedTransaction(State.AWAITING_AMOUNT)))
            }
            whenever {
                viewModel // init viewModel
            }
            then {
                verify(transactionManager, times(2)).newTransactionFlow()
            }
        }

    @Test
    fun `onLoadData would dispatch Cancel if amount is null`() = coroutineTestCase {
        given {
            whenever(transactionManager.newTransactionFlow())
                .thenReturn(flowOf(mockedTransaction(State.AWAITING_CONFIRMATION, amount = null)))
                .thenReturn(flowOf(mockedTransaction(State.SUCCESS)))
        }
        whenever {
            viewModel
        }
        then {
            verify(transactionManager, times(1)).newTransactionFlow()
            verify(transactionManager, times(1)).dispatch(Input.Cancel)
        }
    }

    @Test
    fun `onLoadData would dispatch Cancel if store is null on AWAITING_CONFIRMATION state`() =
        coroutineTestCase {
            given {
                whenever(transactionManager.newTransactionFlow()).thenReturn(
                    flowOf(mockedTransaction(State.AWAITING_CONFIRMATION, store = null))
                ).thenReturn(flowOf(mockedTransaction(State.AWAITING_CONFIRMATION)))
            }
            whenever {
                viewModel
            }
            then {
                verify(transactionManager, times(1)).newTransactionFlow()
                verify(transactionManager, times(1)).dispatch(Input.Cancel)
            }
        }
}