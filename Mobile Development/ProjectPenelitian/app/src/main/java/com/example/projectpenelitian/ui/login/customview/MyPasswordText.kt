package com.example.projectpenelitian.ui.login.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.projectpenelitian.R

class MyPasswordText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {


    private var showPasswordButtonImage: Drawable
    private var hidePasswordButtonImage: Drawable

    private var statusButton = "hidden"
    private var cursorPosition: Int = 0

    init {
        showPasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.baseline_key_24) as Drawable
        hidePasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.baseline_key_off_24) as Drawable
        setOnTouchListener(this)

        hidePasswordIcon()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    setError(resources.getString(R.string.password_format_validate), null)
                } else {
                    error = null
                }

            }

            override fun afterTextChanged(s: Editable) { }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun showPasswordIcon() {
        setButtonDrawables(endOfTheText = showPasswordButtonImage)
    }

    private fun hidePasswordIcon() {
        setButtonDrawables(endOfTheText = hidePasswordButtonImage)
    }


    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText: Drawable? = null, endOfTheText: Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val eyeIconStart: Float
            val eyeIconEnd: Float
            var isShowPasswordIconClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                eyeIconEnd = (showPasswordButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < eyeIconEnd -> isShowPasswordIconClicked = true
                }
            } else {
                eyeIconStart = (width - paddingEnd - showPasswordButtonImage.intrinsicWidth).toFloat()
                when {
                    event.x > eyeIconStart -> isShowPasswordIconClicked = true
                }
            }
            if (isShowPasswordIconClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        return true
                    }
                    MotionEvent.ACTION_UP -> {

                        val currentSelection = selectionStart

                        if (statusButton == "hidden")
                        {
                            statusButton = "show"
                            showPasswordIcon()
                            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                        }else{
                            statusButton = "hidden"
                            hidePasswordIcon()
                            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }

                        setSelection(Math.max(cursorPosition, currentSelection))

                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

}