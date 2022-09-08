package rosa.pay.app.navigation

import rosa.pay.navigation.NavigationDestination
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Singleton
class PayNavigator @Inject constructor() {

    private val _destinationFlow = MutableStateFlow<NavigationDestination?>(null)
    val destinationFlow: StateFlow<NavigationDestination?> = _destinationFlow

    fun navigateTo(navTarget: NavigationDestination) {
        _destinationFlow.value = navTarget
    }

    companion object {
        const val KEY = "navigation"
    }
}