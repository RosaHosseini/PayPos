package rosa.pay.sdk.impl

import rosa.pay.sdk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext

class TransactionManagerImpl : TransactionManager, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob()

    private var stateFlow: MutableStateFlow<Transaction>? = null

    override fun newTransactionFlow(): Flow<Transaction> {
        val currentFlow = stateFlow
        check(currentFlow == null || currentFlow.value.isFinal) {
            "There is already an ongoing transaction!"
        }
        return MutableStateFlow(
            Transaction(
                state = State.PROCESSING,
                amount = null,
                isFinal = false,
                referenceId = null,
                store = null
            )
        ).also {
            stateFlow = it
            launch {
                delay(1000)
                it.update { copy(state = State.AWAITING_AMOUNT) }
            }
        }
    }

    @Suppress("SuspendFunctionOnCoroutineScope")
    override suspend fun dispatch(input: Input) {
        launch {
            val currentFlow = stateFlow
            checkNotNull(currentFlow) { "Start a transaction first!" }
            when (input) {
                is Input.Amount -> {
                    require(currentFlow.value.state == State.AWAITING_AMOUNT) {
                        "The flow is not awaiting an amount!"
                    }
                    delay(1000)
                    currentFlow.update { copy(amount = input.value, state = State.PROCESSING) }
                    delay(1000)
                    currentFlow.update {
                        copy(
                            state = State.AWAITING_CONFIRMATION,
                            store = generateStore()
                        )
                    }
                }
                is Input.Confirm -> {
                    require(currentFlow.value.state == State.AWAITING_CONFIRMATION) {
                        "The flow is not awaiting confirmation!"
                    }
                    delay(1000)
                    currentFlow.update { copy(state = State.PROCESSING) }
                    delay(1000)
                    currentFlow.update {
                        copy(
                            referenceId = "424da38c0242ac120002",
                            isFinal = true,
                            state = if (input.value) State.SUCCESS else State.FAILURE
                        )
                    }
                }
                Input.Cancel -> {
                    require(currentFlow.value.isFinal.not()) { "The flow is in Final state!" }
                    currentFlow.update { copy(isFinal = true, state = State.CANCELLED) }
                    coroutineContext.cancel()
                }
            }
        }
    }

    private fun generateStore(): Store =
        Store(
            name = "Big Kahuna Burger",
            address = "Pulp fiction st. 28, 94",
            postalCode = "2100"
        )

    private fun MutableStateFlow<Transaction>.update(block: Transaction.() -> Transaction) {
        value = block(value)
    }
}