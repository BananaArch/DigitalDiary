package com.banana.digitaldiary.data.local

import android.content.Context
import com.banana.digitaldiary.database.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
//        context.deleteDatabase("app.db")
        return AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
    }
}