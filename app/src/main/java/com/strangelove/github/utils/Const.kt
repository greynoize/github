package com.strangelove.github.utils

import java.text.SimpleDateFormat
import java.util.*

class Const {
    companion object {
        val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("en"))
        val VIEW_DATE_FORMAT = SimpleDateFormat("dd MMM yyyy", Locale("en"))
    }
}