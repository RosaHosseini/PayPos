package rosa.pay.app.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import rosa.pay.app.navigation.PayNaveHost
import rosa.pay.app.navigation.PayNavigator
import rosa.pay.app.ui.theme.PayColor
import rosa.pay.app.ui.theme.PayTheme

@Composable
fun PayApp(navigator: PayNavigator) {
    ProvideWindowInsets {
        PayTheme {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier.padding(),
                backgroundColor = PayColor.GreyBackground,
                contentColor = MaterialTheme.colors.onBackground,
            ) { padding ->
                PayNaveHost(
                    navController = navController,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    navigator = navigator
                )
            }
        }
    }
}