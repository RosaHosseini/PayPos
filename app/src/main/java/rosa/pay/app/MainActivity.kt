package rosa.pay.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import rosa.pay.navigation.PayNavigator
import rosa.pay.app.ui.PayApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: PayNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PayApp(navigator) }
    }
}