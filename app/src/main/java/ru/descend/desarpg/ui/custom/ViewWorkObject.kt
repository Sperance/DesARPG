package ru.descend.desarpg.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ru.descend.desarpg.R
import ru.descend.desarpg.databinding.ViewWorkObjectBinding

class ViewWorkObject @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewWorkObjectBinding = ViewWorkObjectBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        // Получаем атрибуты из XML
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewWorkObject)
        val itemName = typedArray.getString(R.styleable.ViewWorkObject_itemName)
        val timerText = typedArray.getString(R.styleable.ViewWorkObject_timerText)
        val levelText = typedArray.getString(R.styleable.ViewWorkObject_levelText)
        val nextItemText = typedArray.getString(R.styleable.ViewWorkObject_nextItemText)
        val expText = typedArray.getString(R.styleable.ViewWorkObject_expText)
        val progressBarProgress = typedArray.getInt(R.styleable.ViewWorkObject_progressBarProgress, 0)

        // Устанавливаем значения
        binding.itemName.text = itemName
        binding.timerText.text = timerText
        binding.levelText.text = levelText
        binding.nextItemText.text = nextItemText
        binding.expText.text = expText
        binding.progressBar.progress = progressBarProgress

        // Установка атрибутов для item_name
        val itemNameTextColor = typedArray.getColor(R.styleable.ViewWorkObject_itemNameTextColor, ContextCompat.getColor(context, R.color.white))
        val itemNameTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewWorkObject_itemNameTextSize, 18).toFloat()
        val itemNameTypeface = typedArray.getInt(R.styleable.ViewWorkObject_itemNameTypeface, 0)
        binding.itemName.setTextColor(itemNameTextColor)
        binding.itemName.textSize = itemNameTextSize
        binding.itemName.setTypeface(null, itemNameTypeface)

        // Установка атрибутов для timer_text
        val timerTextColor = typedArray.getColor(R.styleable.ViewWorkObject_timerTextColor, ContextCompat.getColor(context, R.color.white))
        val timerTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewWorkObject_timerTextSize, 18).toFloat()
        val timerTypeface = typedArray.getInt(R.styleable.ViewWorkObject_timerTypeface, 0)
        binding.timerText.setTextColor(timerTextColor)
        binding.timerText.textSize = timerTextSize
        binding.timerText.setTypeface(null, timerTypeface)

        // Установка атрибутов для level_text
        val levelTextColor = typedArray.getColor(R.styleable.ViewWorkObject_levelTextColor, ContextCompat.getColor(context, R.color.white))
        val levelTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewWorkObject_levelTextSize, 18).toFloat()
        val levelTypeface = typedArray.getInt(R.styleable.ViewWorkObject_levelTypeface, 0)
        binding.levelText.setTextColor(levelTextColor)
        binding.levelText.textSize = levelTextSize
        binding.levelText.setTypeface(null, levelTypeface)

        // Установка атрибутов для next_item_text
        val nextItemTextColor = typedArray.getColor(R.styleable.ViewWorkObject_nextItemTextColor, ContextCompat.getColor(context, R.color.white))
        val nextItemTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewWorkObject_nextItemTextSize, 18).toFloat()
        val nextItemTypeface = typedArray.getInt(R.styleable.ViewWorkObject_nextItemTypeface, 0)
        binding.nextItemText.setTextColor(nextItemTextColor)
        binding.nextItemText.textSize = nextItemTextSize
        binding.nextItemText.setTypeface(null, nextItemTypeface)

        // Установка атрибутов для exp_text
        val expTextColor = typedArray.getColor(R.styleable.ViewWorkObject_expTextColor, ContextCompat.getColor(context, R.color.white))
        val expTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewWorkObject_expTextSize, 18).toFloat()
        val expTypeface = typedArray.getInt(R.styleable.ViewWorkObject_expTypeface, 0)
        binding.expText.setTextColor(expTextColor)
        binding.expText.textSize = expTextSize
        binding.expText.setTypeface(null, expTypeface)

        typedArray.recycle()
    }

    fun setItemImage(@DrawableRes drawableRes: Int) {
        binding.itemImage.setImageResource(drawableRes)
    }

    fun setItemName(name: String?) {
        binding.itemName.text = name
    }

    fun setTimerText(time: String) {
        binding.timerText.text = time
    }

    fun setLevelText(level: String) {
        binding.levelText.text = level
    }

    fun setNextItemText(nextItem: String) {
        binding.nextItemText.text = nextItem
    }

    fun setExpText(exp: String) {
        binding.expText.text = exp
    }

    fun getProgressBar() = binding.progressBar

    fun getTextProgress() = binding.textProgress

    fun setItemNameTextColor(@ColorInt color: Int) {
        binding.itemName.setTextColor(color)
    }

    fun setTimerTextColor(@ColorInt color: Int) {
        binding.timerText.setTextColor(color)
    }

    fun setLevelTextColor(@ColorInt color: Int) {
        binding.levelText.setTextColor(color)
    }

    fun setNextItemTextColor(@ColorInt color: Int) {
        binding.nextItemText.setTextColor(color)
    }

    fun setExpTextColor(@ColorInt color: Int) {
        binding.expText.setTextColor(color)
    }

    fun setItemNameTextSize(size: Float) {
        binding.itemName.textSize = size
    }

    fun setTimerTextSize(size: Float) {
        binding.timerText.textSize = size
    }

    fun setLevelTextSize(size: Float) {
        binding.levelText.textSize = size
    }

    fun setNextItemTextSize(size: Float) {
        binding.nextItemText.textSize = size
    }

    fun setExpTextSize(size: Float) {
        binding.expText.textSize = size
    }

    fun setItemNameTypeface(typeface: Int) {
        binding.itemName.setTypeface(null, typeface)
    }

    fun setTimerTypeface(typeface: Int) {
        binding.timerText.setTypeface(null, typeface)
    }

    fun setLevelTypeface(typeface: Int) {
        binding.levelText.setTypeface(null, typeface)
    }

    fun setNextItemTypeface(typeface: Int) {
        binding.nextItemText.setTypeface(null, typeface)
    }

    fun setExpTypeface(typeface: Int) {
        binding.expText.setTypeface(null, typeface)
    }
}