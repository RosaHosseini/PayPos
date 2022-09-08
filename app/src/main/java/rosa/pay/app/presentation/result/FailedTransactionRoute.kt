package rosa.pay.app.presentation.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import rosa.pay.app.R
import rosa.pay.app.presentation.TransactionViewModel
import rosa.pay.app.model.Money
import rosa.pay.app.model.TransactionUiState
import rosa.pay.app.presentation.component.DisableBackButton
import rosa.pay.app.ui.theme.PayColor

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