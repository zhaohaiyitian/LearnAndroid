package com.jack.learn.kotlin

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


private typealias AfterTextChanged = (s: Editable?)->Unit
private typealias OnTextChanged = (s: CharSequence?, start: Int, before: Int, count: Int)->Unit
class MTextWatcher:TextWatcher {

    private var onTextChanged:OnTextChanged? = null
    private var afterTextChanged:AfterTextChanged? = null
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        this.onTextChanged?.invoke(s,start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        this.afterTextChanged?.invoke(s)
    }

    fun afterTextChanged(function: AfterTextChanged) {
        this.afterTextChanged = function
    }

    fun onTextChanged(function: OnTextChanged) {
        this.onTextChanged = function
    }
}

fun EditText.textChange(mTextWatcher: MTextWatcher.()->Unit) {
    this.addTextChangedListener(MTextWatcher().apply(mTextWatcher))
}