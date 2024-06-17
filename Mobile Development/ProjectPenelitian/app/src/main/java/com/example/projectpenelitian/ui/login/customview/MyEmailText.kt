package com.example.projectpenelitian.ui.login.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.projectpenelitian.R

class MyEmailText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    private var isEmailValid = false

    init {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(text.toString().trim()).matches()
                error = if (!isEmailValid) {
                    resources.getString(R.string.email_format_validate)
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        return false
    }
}