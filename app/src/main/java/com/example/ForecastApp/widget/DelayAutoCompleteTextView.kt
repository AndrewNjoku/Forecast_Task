package com.example.ForecastApp.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

class DelayAutoCompleteTextView(context: Context, attrs: AttributeSet) : AppCompatAutoCompleteTextView(context, attrs) {

    private var mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY
    private var mLoadingIndicator: ProgressBar? = null

    @SuppressLint("HandlerLeak") //we clean up onDetachFromWindow
    private var mHandler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message) {
            super@DelayAutoCompleteTextView.performFiltering(msg.obj as CharSequence, msg.arg1)
        }
    }

    fun setLoadingIndicator(progressBar: ProgressBar) {
        mLoadingIndicator = progressBar
    }

    fun setAutoCompleteDelay(autoCompleteDelay: Int) {
        mAutoCompleteDelay = autoCompleteDelay
    }

    override fun performFiltering(text: CharSequence, keyCode: Int) {
        if (mLoadingIndicator != null) {
            mLoadingIndicator!!.visibility = View.VISIBLE
        }
        mHandler!!.removeMessages(MESSAGE_TEXT_CHANGED)
        mHandler!!.sendMessageDelayed(mHandler!!.obtainMessage(MESSAGE_TEXT_CHANGED, text), mAutoCompleteDelay.toLong())
    }

    override fun onFilterComplete(count: Int) {
        if (mLoadingIndicator != null) {
            mLoadingIndicator!!.visibility = View.GONE
        }
        super.onFilterComplete(count)
    }

    override fun onDetachedFromWindow() {
        //clean up to avoid leaks
        mHandler!!.removeMessages(MESSAGE_TEXT_CHANGED)
        mHandler = null
        super.onDetachedFromWindow()
    }

    companion object {

        private val MESSAGE_TEXT_CHANGED = 100
        private val DEFAULT_AUTOCOMPLETE_DELAY = 750
    }
}
