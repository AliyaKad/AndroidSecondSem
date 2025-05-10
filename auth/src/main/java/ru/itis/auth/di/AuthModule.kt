package ru.itis.auth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.itis.auth.data.database.AuthDatabase
import ru.itis.auth.data.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthDatabase(@ApplicationContext context: Context): AuthDatabase {
        return AuthDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(database: AuthDatabase): AuthRepository {
        return AuthRepository(database)
    }
}