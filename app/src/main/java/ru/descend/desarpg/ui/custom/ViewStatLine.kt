package ru.descend.desarpg.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import ru.descend.desarpg.R
import ru.descend.desarpg.databinding.ViewStatLineBinding
import ru.descend.desarpg.model.StockStatsProp
import ru.descend.desarpg.to0Text

class ViewStatLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewStatLineBinding = ViewStatLineBinding.inflate(LayoutInflater.from(context), this, true)

    private var leftIcon: Drawable? = null
    private var text: CharSequence = ""
    private var textPrefix: CharSequence = ""
    private var textPostfix: CharSequence = ""

    @ColorInt private var textColor: Int = ContextCompat.getColor(context, R.color.defaultText)
    private var textSize: Float = 14f
    private var textGravity: Int = Gravity.CENTER_VERTICAL or Gravity.START

    @ColorInt private var prefixTextColor: Int = ContextCompat.getColor(context, R.color.defaultText)
    private var prefixTextSize: Float = 14f
    private var prefixTextStyle: Int = Typeface.BOLD

    @ColorInt private var postfixTextColor: Int = ContextCompat.getColor(context, R.color.defaultText)
    private var postfixTextSize: Float = 14f
    private var postfixTextStyle: Int = Typeface.BOLD

    private var mainTextStyle: Int = Typeface.NORMAL

    private var cardStrokeColor: Int = Color.TRANSPARENT
    private var cardStrokeWidth: Float = 0f

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewStatLine, defStyleAttr, 0)
        try {
            leftIcon = a.getDrawable(R.styleable.ViewStatLine_leftIcon)
            text = a.getString(R.styleable.ViewStatLine_text) ?: ""
            textPrefix = a.getString(R.styleable.ViewStatLine_textPrefix) ?: ""
            textPostfix = a.getString(R.styleable.ViewStatLine_textPostfix) ?: ""

            textColor = a.getColor(R.styleable.ViewStatLine_textColor, ContextCompat.getColor(context, R.color.defaultText))
            textSize = a.getDimension(R.styleable.ViewStatLine_textSize, 14f)
            textGravity = a.getInt(R.styleable.ViewStatLine_textGravity, Gravity.CENTER_VERTICAL or Gravity.START)

            prefixTextColor = a.getColor(R.styleable.ViewStatLine_prefixTextColor, ContextCompat.getColor(context, R.color.defaultText))
            prefixTextSize = a.getDimension(R.styleable.ViewStatLine_prefixTextSize, 14f)
            prefixTextStyle = a.getInt(R.styleable.ViewStatLine_prefixTextStyle, Typeface.BOLD)

            postfixTextColor = a.getColor(R.styleable.ViewStatLine_postfixTextColor, ContextCompat.getColor(context, R.color.defaultText))
            postfixTextSize = a.getDimension(R.styleable.ViewStatLine_postfixTextSize, 14f)
            postfixTextStyle = a.getInt(R.styleable.ViewStatLine_postfixTextStyle, Typeface.BOLD)

            mainTextStyle = a.getInt(R.styleable.ViewStatLine_mainTextStyle, Typeface.NORMAL)

            cardStrokeColor = a.getColor(R.styleable.ViewStatLine_cardStrokeColor, Color.TRANSPARENT)
            cardStrokeWidth = a.getDimension(R.styleable.ViewStatLine_cardStrokeWidth, 0f)
        } finally {
            a.recycle()
        }
        applyValues()
    }

    private fun applyValues() {
        binding.apply {
            leftIcon?.let {
                iconStart.setImageDrawable(it)
                iconStart.visibility = View.VISIBLE
            } ?: run { iconStart.visibility = View.GONE }
            textPrefixView.text = textPrefix
            textPrefixView.setTextColor(prefixTextColor)
            textPrefixView.textSize = prefixTextSize
            textPrefixView.typeface = Typeface.create(Typeface.SERIF, prefixTextStyle)
            textPostfixView.text = textPostfix
            textPostfixView.setTextColor(postfixTextColor)
            textPostfixView.textSize = postfixTextSize
            textPostfixView.typeface = Typeface.create(Typeface.SERIF, postfixTextStyle)
            textMain.text = text
            textMain.setTextColor(textColor)
            textMain.textSize = textSize
            textMain.gravity = textGravity
            textMain.typeface = Typeface.create(Typeface.SERIF, mainTextStyle)

            cardView.strokeColor = cardStrokeColor
            cardView.strokeWidth = cardStrokeWidth.toInt()
        }
        invalidate()
    }

    fun setLeftIcon(icon: Drawable?) {
        leftIcon = icon
        applyValues()
    }

    fun setLeftIcon(resId: Int) {
        setLeftIcon(ContextCompat.getDrawable(context, resId))
    }

    fun setText(text: CharSequence) {
        this.text = text
        applyValues()
    }

    fun setMaybeText(text: CharSequence?) {
        if (text == null || text.trim().isBlank()) setVisibility(false)
        else {
            this.text = text.toString()
            setVisibility(true)
        }
        applyValues()
    }

    fun setMaybeText(text: Number?) {
        if (text == null || text == 0) setVisibility(false)
        else {
            this.text = text.toString()
            setVisibility(true)
        }
        applyValues()
    }

    fun setProperty(prop: StockStatsProp?) {
        if (prop == null) return
        val textSymbol = if (prop.getPercent() < 0) "-" else "+"
        val needPercent = prop.getPercent().to0Text().trim() != "0"
        this.text = prop.getCurrentForGlobalStats().to0Text()
        if (needPercent) this.textPostfix = " (${prop.getValue().to0Text()} $textSymbol${prop.getPercent().to0Text()}%)"
        else this.textPostfix = ""
        applyValues()
    }

    fun setVisibility(visible: Boolean) {
        this.visibility = if (visible) VISIBLE else GONE
    }

    fun isVisible(): Boolean {
        return visibility == VISIBLE
    }

    fun setTextPrefix(textPrefix: CharSequence) {
        this.textPrefix = textPrefix
        applyValues()
    }

    fun setTextPostfix(textPostfix: CharSequence) {
        this.textPostfix = textPostfix
        applyValues()
    }

    fun setTextColor(@ColorInt color: Int) {
        textColor = color
        applyValues()
    }

    fun setTextSize(@Dimension size: Float) {
        textSize = size
        applyValues()
    }

    fun setTextGravity(gravity: Int) {
        textGravity = gravity
        applyValues()
    }

    fun setPrefixTextColor(@ColorInt color: Int) {
        prefixTextColor = color
        applyValues()
    }

    fun setPrefixTextSize(@Dimension size: Float) {
        prefixTextSize = size
        applyValues()
    }

    fun setPrefixTextStyle(style: Int) {
        prefixTextStyle = style
        applyValues()
    }

    fun setPostfixTextColor(@ColorInt color: Int) {
        postfixTextColor = color
        applyValues()
    }

    fun setPostfixTextSize(@Dimension size: Float) {
        postfixTextSize = size
        applyValues()
    }

    fun setPostfixTextStyle(style: Int) {
        postfixTextStyle = style
        applyValues()
    }

    fun setMainTextStyle(style: Int) {
        mainTextStyle = style
        applyValues()
    }

    fun setCardStrokeColor(@ColorInt color: Int) {
        cardStrokeColor = color
        applyValues()
    }

    fun setCardStrokeWidth(@Dimension width: Float) {
        cardStrokeWidth = width
        applyValues()
    }
}