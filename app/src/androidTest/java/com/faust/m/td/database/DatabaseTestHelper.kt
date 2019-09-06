package com.faust.m.td.database

import android.database.Cursor

fun Cursor.getStringFrom(columnName: String): String = getString(getColumnIndex(columnName))
fun Cursor.getLongFrom(columnName: String) = getLong(getColumnIndex(columnName))