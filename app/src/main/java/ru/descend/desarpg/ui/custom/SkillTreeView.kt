package ru.descend.desarpg.ui.custom

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import io.objectbox.Box
import io.objectbox.BoxStore
import ru.descend.desarpg.AppController
import ru.descend.desarpg.R
import ru.descend.desarpg.log
import ru.descend.desarpg.model.SkillNodeEntity
import ru.descend.desarpg.ui.NodeBottomSheetFragment
import kotlin.math.sqrt

class SkillTreeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val nodes = mutableListOf<SkillNode>()
    private val connections = mutableListOf<Pair<SkillNode, SkillNode>>()
    private val nodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private val activatedNodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
        style = Paint.Style.FILL
    }
    private val connectionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = 5f
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 40f
    }

    private var offsetX = 0f
    private var offsetY = 0f
    private var scaleFactor = 1f
    private val gestureDetector = GestureDetector(context, GestureListener())

    // ObjectBox
//    private lateinit var skillNodeBox: Box<SkillNodeEntity>

    init {
        // Инициализация ObjectBox
//        val boxStore = (context.applicationContext as AppController).boxStore
//        skillNodeBox = boxStore.boxFor(SkillNodeEntity::class.java)

        // Загрузка узлов из базы данных
//        loadNodesFromDatabase()

        // Если узлов нет, добавляем пример
        if (nodes.isEmpty()) {
            val node1 = SkillNode(PointF(100f, 100f), "Node 1")
            val node2 = SkillNode(PointF(300f, 300f), "Node 2")
            val node3 = SkillNode(PointF(500f, 100f), "Node 3", ContextCompat.getDrawable(context, R.drawable.ic_android_black_24dp))

            nodes.add(node1)
            nodes.add(node2)
            nodes.add(node3)

            connections.add(node1 to node2)
            connections.add(node2 to node3)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(offsetX, offsetY)
        canvas.scale(scaleFactor, scaleFactor)

        // Отрисовка связей
        for (connection in connections) {
            val (start, end) = connection

            // Определяем цвет линии в зависимости от состояния узлов
            val lineColor = if (start.isActivated && end.isActivated) {
                Color.GREEN // Зеленый, если оба узла активированы
            } else {
                Color.RED // Красный, если хотя бы один узел не активирован
            }

            // Устанавливаем цвет линии
            connectionPaint.color = lineColor

            // Рисуем линию
            canvas.drawLine(start.position.x, start.position.y, end.position.x, end.position.y, connectionPaint)
        }

        // Отрисовка узлов
        for (node in nodes) {
            val paint = if (node.isActivated) activatedNodePaint else nodePaint

            // Рисуем круг (фон узла)
            canvas.drawCircle(node.position.x, node.position.y, 50f, paint)

            // Если есть иконка, рисуем её
            node.icon?.let { icon ->
                val iconSize = 80 // Размер иконки
                val halfIconSize = iconSize / 2

                // Устанавливаем границы для иконки
                icon.setBounds(
                    (node.position.x - halfIconSize).toInt(),
                    (node.position.y - halfIconSize).toInt(),
                    (node.position.x + halfIconSize).toInt(),
                    (node.position.y + halfIconSize).toInt()
                )

                // Рисуем иконку
                icon.draw(canvas)
            }

            // Рисуем текст (название узла)
            canvas.drawText(node.name, node.position.x - 40f, node.position.y - 60f, textPaint)
        }

        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (gestureDetector.onTouchEvent(event)) {
            return true
        }

        when (event.action) {
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_DOWN -> {
                val x = (event.x - offsetX) / scaleFactor
                val y = (event.y - offsetY) / scaleFactor
                val clickedNode = findNodeAt(x, y)
                if (clickedNode != null) {
                    showNodeDialog(clickedNode)
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun findNodeAt(x: Float, y: Float): SkillNode? {
        for (node in nodes) {
            val dx = x - node.position.x
            val dy = y - node.position.y
            val distance = sqrt(dx * dx + dy * dy)
            if (distance <= 50f) { // 50f - радиус узла
                return node
            }
        }
        return null
    }

    private fun canActivateNode(node: SkillNode): Boolean {
        // Узел может быть активирован, если он связан с хотя бы одним активированным узлом
        for (connection in connections) {
            val (start, end) = connection
            if ((start == node && end.isActivated) || (end == node && start.isActivated)) {
                return true
            }
        }
        // Если узел первый в дереве, его можно активировать
        return nodes.indexOf(node) == 0
    }

    private fun showNodeDialog(node: SkillNode) {
        val fragment = NodeBottomSheetFragment().apply {
            setNode(node)
            setActivationListener(object : NodeBottomSheetFragment.OnNodeActivationListener {
                override fun onActivateNodeRequested(node: SkillNode) {
                    if (canActivateNode(node)) {
                        node.isActivated = true
                        saveNodesToDatabase()
                        invalidate()
                    } else {
                        showActivationError()
                    }
                }

                override fun onDeactivateNodeRequested(node: SkillNode) {
                    if (canDeactivateNode(node)) {
                        node.isActivated = false
                        saveNodesToDatabase()
                        invalidate()
                    } else {
                        showDeactivationError()
                    }
                }
            })
        }

        (context as? FragmentActivity)?.supportFragmentManager?.let {
            fragment.show(it, NodeBottomSheetFragment.TAG)
        }
    }

    private fun canDeactivateNode(node: SkillNode): Boolean {
        if (!node.isActivated) return false // Узел не активирован

        // Проверяем, что узел связан только с одним активированным узлом
        val connectedActivatedNodes = connections
            .filter { it.first == node || it.second == node }
            .map { if (it.first == node) it.second else it.first }
            .filter { it.isActivated }

        // Если это корневой узел, его можно деактивировать только если нет связанных активированных узлов
        if (nodes.indexOf(node) == 0) {
            return connectedActivatedNodes.isEmpty()
        }

        // Для остальных узлов: можно деактивировать, если связан только с одним активированным узлом
        return connectedActivatedNodes.size == 1
    }

    private fun showActivationError() {
        AlertDialog.Builder(context)
            .setTitle("Ошибка")
            .setMessage("Узел не может быть активирован. Нет связи с активированным узлом.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showDeactivationError() {
        AlertDialog.Builder(context)
            .setTitle("Ошибка")
            .setMessage("Узел не может быть деактивирован. Он связан с несколькими активированными узлами.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun loadNodesFromDatabase() {
//        val skillNodeEntities = skillNodeBox.all
//        for (entity in skillNodeEntities) {
//            val node = SkillNode(PointF(entity.positionX, entity.positionY), entity.name, entity.isActivated)
//            nodes.add(node)
//        }
    }

    private fun saveNodesToDatabase() {
//        skillNodeBox.removeAll() // Очищаем базу данных перед сохранением
//        for (node in nodes) {
//            val entity = SkillNodeEntity(
//                name = node.name,
//                positionX = node.position.x,
//                positionY = node.position.y,
//                isActivated = node.isActivated
//            )
//            skillNodeBox.put(entity)
//        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean = true

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            offsetX -= distanceX
            offsetY -= distanceY
            invalidate()
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            scaleFactor = if (scaleFactor == 1f) 2f else 1f
            invalidate()
            return true
        }
    }

    data class SkillNode(
        val position: PointF,
        val name: String,
        val icon: Drawable? = null,
        var isActivated: Boolean = false
    )
}