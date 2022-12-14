package rosa.pay.transaction.presentation.amount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import rosa.pay.core.AppDispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class KeyPadViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers
) : ViewModel() {

    private val _number = MutableStateFlow<Long>(0)
    val number: StateFlow<Long> = _number

    val isKeypadEnabled: StateFlow<Boolean> = number.map {
        it.toString().count() < CHAR_LIMIT
    }.stateIn(viewModelScope, SharingStarted.Lazily, true)

    fun processInput(button: Char) {
        viewModelScope.launch(appDispatchers.default) {
            _number.value = when (button) {
                CLEAR -> {
                    number.value.clear()
                }
                BACK_SPACE -> {
                    number.value.backSpace()
                }
                else -> {
                    number.value.appendNumber(button.toString().toInt())
                }
            }
        }
    }

    companion object {
        private const val BACK_SPACE = '<'
        private const val CLEAR = 'C'
        private const val CHAR_LIMIT = 8
        val controlKeys = arrayOf(BACK_SPACE, CLEAR)
        val offerKeypad = arrayOf(
            arrayOf('1', '2', '3'),
            arrayOf('4', '5', '6'),
            arrayOf('7', '8', '9'),
            arrayOf(CLEAR, '0', BACK_SPACE),
        )
    }
}