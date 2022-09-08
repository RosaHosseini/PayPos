package rosa.pay.sdk.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rosa.pay.sdk.TransactionManager
import rosa.pay.sdk.impl.TransactionManagerImpl

@InstallIn(SingletonComponent::class)
@Module
class SdkModule {
    @Provides
    fun provideTransactionManager(): TransactionManager = TransactionManagerImpl()
}