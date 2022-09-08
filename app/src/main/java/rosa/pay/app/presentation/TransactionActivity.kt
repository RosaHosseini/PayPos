package rosa.pay.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import rosa.pay.app.navigation.PayNavigator
import javax.inject.Inject

@AndroidEntryPoint
class TransactionActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: PayNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PayApp(navigator) }
    }
}