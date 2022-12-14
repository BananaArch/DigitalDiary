package com.banana.digitaldiary.android.di

import android.app.Application
import com.banana.digitaldiary.database.AppDatabase
import com.banana.digitaldiary.domain.contact.ContactDataSource
import com.banana.digitaldiary.data.contact.SqlDelightContactDataSource
import com.banana.digitaldiary.data.local.DatabaseDriverFactory
import com.banana.digitaldiary.data.note.SqlDelightNoteDataSource
import com.banana.digitaldiary.data.reminder.SqlDelightReminderDataSource
import com.banana.digitaldiary.domain.note.NoteDataSource
import com.banana.digitaldiary.domain.reminder.ReminderDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(AppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideContactDataSource(driver: SqlDriver): ContactDataSource {
        return SqlDelightContactDataSource(AppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideReminderDataSource(driver: SqlDriver): ReminderDataSource {
        return SqlDelightReminderDataSource(AppDatabase(driver))
    }

}