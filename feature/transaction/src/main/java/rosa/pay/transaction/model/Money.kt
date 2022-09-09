package rosa.pay.transaction.model

import android.os.Parcelable
import rosa.pay.transaction.presentation.amount.getFormattedNumber
import kotlinx.parcelize.Parcelize

@Parcelize
data class Money(
    val amount: Long,
    val currency: Currency = Currency(name = "Dollar", symbol = "$")
) : Parcelable {
    override fun toString(): String {
        return "${currency.symbol}${amount.getFormattedNumber()}"
    }
}

@Parcelize
data class Currency(
    val name: String,
    val symbol: String
) : Parcelable