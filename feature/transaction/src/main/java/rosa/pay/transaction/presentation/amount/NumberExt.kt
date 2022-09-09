package rosa.pay.transaction.presentation.amount

import java.util.*

fun Long.getFormattedNumber(): String {
    return try {
        "%,d".format(this)
    } catch (ignored: IllegalFormatConversionException) {
        this.toString()
    }
}

fun Long.appendNumber(input: Int): Long {
    return if (this == 0L) {
        input.toLong()
    } else {
        (toString() + input.toString()).toLong()
    }
}

fun Long.clear(): Long {
    return 0
}

fun Long.backSpace(): Long {
    return if (toString().count() == 1) {
        0
    } else {
        toString().dropLast(1).toLong()
    }
}