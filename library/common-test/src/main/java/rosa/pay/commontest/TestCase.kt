package rosa.pay.commontest

import kotlinx.coroutines.test.TestScope

class TestCase {

    var given: (() -> Unit)? = null
    var whenever: (() -> Unit)? = null
    var then: (() -> Unit)? = null

    fun given(block: () -> Unit) {
        given = block
    }

    fun whenever(block: () -> Unit) {
        whenever = block
    }

    fun <T> whenever(input: T, block: T.() -> Unit) {
        whenever = {
            block(input)
        }
    }

    fun then(block: () -> Unit) {
        then = block
    }
}

class SuspendTestCase {

    var given: (suspend TestScope.() -> Unit)? = null
    var whenever: (suspend TestScope.() -> Unit)? = null
    var then: (suspend TestScope.() -> Unit)? = null

    fun given(block: suspend TestScope.() -> Unit) {
        given = block
    }

    fun whenever(block: suspend TestScope.() -> Unit) {
        whenever = block
    }

    fun <T> whenever(input: T, block: suspend T.() -> Unit) {
        whenever = {
            block(input)
        }
    }

    fun then(block: suspend TestScope.() -> Unit) {
        then = block
    }
}
