package com.jainhardik120.bluetoothlescanner.di

import android.content.Context
import com.jainhardik120.bluetoothlescanner.domain.BluetoothController
import com.jainhardik120.bluetoothlescanner.data.BLEController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothController {
        return BLEController(context)
    }
}