package com.tbright.ktbaseproject.demo.ui

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.blankj.utilcode.util.SPUtils

class TestProvider : ContentProvider() {
    companion object{
        const val TEST_URI = "content://com.tbright.ktbaseproject.demo/insert"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        init {
            uriMatcher.addURI("com.tbright.ktbaseproject.demo", "insert", 0)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            0->{
                SPUtils.getInstance().put("AAA","AAA")
                context?.contentResolver?.notifyChange(uri, null)
            }
        }
        return uri
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        var cursor: Cursor? = null
        when (uriMatcher.match(uri)) {
            0->{
                val matrixCursor = MatrixCursor(arrayOf("AAA"))
                matrixCursor.addRow(arrayOf(SPUtils.getInstance().getString("AAA")))
                cursor = matrixCursor
            }
        }

        return cursor
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 1
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}