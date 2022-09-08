package rosa.pay.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import rosa.pay.navigation.NavigationDestination
import rosa.pay.app.presentation.TransactionViewModel
import rosa.pay.app.presentation.amount.AwaitingAmountRoute
import rosa.pay.app.presentation.confirmation.ConfirmTransactionRoute
import rosa.pay.app.presentation.result.FailedTransactionRoute
import rosa.pay.app.presentation.loading.LoadingScreen
import rosa.pay.app.presentation.result.SucceedTransactionRoute

object LoadingDestination : NavigationDestination {
    override val route = "loading_route"
}

object AwaitingAmountDestination : NavigationDestination {
    override val route = "awaiting_amount_route"
}

object AwaitingConfirmationDestination : NavigationDestination {
    override val route = "awaiting_confirmation_route"
}

object TransactionSucceedDestination : NavigationDestination {
    override val route = "transaction_success_route"
}

object TransactionFailureDestination : NavigationDestination {
    override val route = "transaction_failure_route"
}

fun NavGraphBuilder.transactionGraph(viewModel: TransactionViewModel) {
    composable(route = LoadingDestination.route) {
        LoadingScreen()
    }
    composable(route = AwaitingAmountDestination.route) {
        AwaitingAmountRoute(viewModel)
    }
    composable(route = AwaitingConfirmationDestination.route) {
        ConfirmTransactionRoute(viewModel)
    }
    composable(route = TransactionSucceedDestination.route) {
        SucceedTransactionRoute(viewModel)
    }
    composable(route = TransactionFailureDestination.route) {
        FailedTransactionRoute(viewModel)
    }
}