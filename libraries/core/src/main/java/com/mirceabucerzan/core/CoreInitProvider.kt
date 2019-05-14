package com.mirceabucerzan.core

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import timber.log.Timber

/**
 * [ContentProvider] with a single purpose: perform library initialization.
 *
 * Will provide automatic init for clients, since it will be created before
 * all other Android components and it will be present in the merged manifest.
 */
class CoreInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        // Initialize Timber logging library
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            CoreLog.d("Planted Timber debug tree")
        } else {
            Timber.plant(ReleaseTree())
            CoreLog.d("Planted Timber release tree")
        }

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null
}