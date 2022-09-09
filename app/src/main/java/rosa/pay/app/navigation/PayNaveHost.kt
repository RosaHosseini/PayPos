package rosa.pay.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import rosa.pay.transaction.presentation.TransactionViewModel
import kotlinx.coroutines.flow.filterNotNull
import rosa.pay.navigation.PayNavigator
import rosa.pay.transaction.navigation.AwaitingAmountDestination
import rosa.pay.transaction.navigation.transactionGraph

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun PayNaveHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AwaitingAmountDestination.route,
    navigator: PayNavigator
) {
    LaunchedEffect(PayNavigator.KEY) {
        navigator.destinationFlow.filterNotNull().collect {
            if (navController.currentDestination?.route != it.route) {
                navController.navigate(it.route)
            }
        }
    }
    val transactionViewModel = hiltViewModel<TransactionViewModel>()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        transactionGraph(transactionViewModel)
    }
}