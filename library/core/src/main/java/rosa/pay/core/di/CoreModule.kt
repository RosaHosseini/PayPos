package rosa.pay.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rosa.pay.core.AppDispatchers
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class CoreModule {
    @Provides
    fun provideAppDispatcher() =
        AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default)
}