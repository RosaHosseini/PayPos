package rosa.pay.transaction.presentation.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import rosa.pay.transaction.presentation.TransactionViewModel
import rosa.pay.transaction.presentation.component.DisableBackButton
import rosa.pay.app.ui.theme.PayColor
import rosa.pay.transaction.R
import rosa.pay.transaction.model.Money
import rosa.pay.transaction.model.TransactionUiState

@Composable
fun FailedTransactionRoute(viewModel: TransactionViewModel) {
    DisableBackButton()
    val args by rememberSaveable {
        mutableStateOf(viewModel.transactionUiState.value as TransactionUiState.Failure)
    }
    FailedTransactionScreen(
        transactionAmount = args.transactionAmount,
        onRetryPayment = viewModel::onReset
    )
}

@Composable
fun FailedTransactionScreen(
    transactionAmount: Money,
    onRetryPayment: () -> Unit
) {
    TransactionResultScreen(
        iconResource = R.drawable.failure,
        description = stringResource(id = R.string.failed_transaction),
        transactionAmount = transactionAmount,
        actionButtonText = stringResource(id = R.string.back_to_dashboard),
        primaryColor = PayColor.Red20,
        onActionButtonClick = onRetryPayment
    )
}

@Preview(showBackground = true)
@Composable
private fun FailedTransactionPreview() {
    FailedTransactionScreen(transactionAmount = Money(13000)) { }
}