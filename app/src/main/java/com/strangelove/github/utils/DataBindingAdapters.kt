package com.strangelove.github.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.strangelove.github.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions().placeholder(ContextCompat.getDrawable(view.context, R.drawable.place_holder)))
        .into(view)
}

@BindingAdapter("app:dateFromString")
fun dateFromString(textView: TextView, dateInString: String?) {
    if (dateInString != null) {
        val date = Const.DATE_FORMAT.parse(dateInString)
        textView.text = textView.context.getString(R.string.profile_createdAt, Const.VIEW_DATE_FORMAT.format(date))
    }
}


