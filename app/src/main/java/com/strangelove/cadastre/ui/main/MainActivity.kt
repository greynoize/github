package com.strangelove.cadastre.ui.main

import android.databinding.Observable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.strangelove.cadastre.BR
import com.strangelove.cadastre.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId == BR.click) {
                    counter++
                    textView.text = "Click $counter"
                }
            }
        })

        button.onClick {
            mainViewModel.onClick()
        }
    }
}
