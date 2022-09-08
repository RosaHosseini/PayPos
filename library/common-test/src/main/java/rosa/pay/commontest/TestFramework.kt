package rosa.pay.commontest

import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

inline fun testCase(testCase: TestCase.() -> Unit) {
    TestCase().apply(testCase).apply {
        given?.invoke()
        whenever?.invoke() ?: throw NotImplementedError("You need to implement whenever")
        then?.invoke() ?: throw NotImplementedError("You need to implement then")
    }
}

inline fun coroutineTestCase(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline testCase: SuspendTestCase.() -> Unit
) {
    runTest(context) {
        SuspendTestCase().apply(testCase).apply {
            given?.invoke(this@runTest)
            whenever?.invoke(this@runTest)
            then?.invoke(this@runTest) ?: throw NotImplementedError("You need to implement then ")
        }
    }
}