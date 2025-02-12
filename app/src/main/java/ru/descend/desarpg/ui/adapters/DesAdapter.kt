package ru.descend.desarpg.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.descend.desarpg.log
import kotlin.reflect.KClass
import kotlin.reflect.full.functions

abstract class DesAdapter<T, VM: ViewBinding>(private val classBinding: KClass<*>) : RecyclerView.Adapter<DesAdapter<T, VM>.ViewHolder>() {

    inner class ViewHolder(data: ViewBinding) : RecyclerView.ViewHolder(data.root) {
        var binding: ViewBinding
        init {
            binding = data
            itemView.setOnClickListener {
                onClicked?.invoke(adapterList[adapterPosition])
            }
            itemView.setOnLongClickListener {
                onLongClicked?.invoke(adapterList[adapterPosition])
                true
            }
        }
    }

    private var adapterList = ArrayList<T>()

    /**
     * Обработка события нажатия на элемент списка
     */
    var onClicked: ((T) -> Any)? = null

    /**
     * Обработка события нажатия и удержания элемента списка
     */
    var onLongClicked: ((T) -> Any)? = null

    /**
     * Метод привязки объекта к UI
     */
    abstract fun onBindItem(item: T, binding: VM)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val instances = classBinding.functions.last().call(LayoutInflater.from(parent.context), parent, false) as VM
        return ViewHolder(instances)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindItem(adapterList[position], holder.binding as VM)
    }

    /**
     * Количество элементов адаптера
     */
    override fun getItemCount() = adapterList.size

    open fun isAreItemsTheSame(itemNew: T, itemOld: T): Boolean? = null
    open fun isAreContentsTheSame(itemNew: T, itemOld: T): Boolean? = null

    fun getItem(index: Int): T? {
        return try {
            adapterList[index]
        } catch (e: Exception) {
            log("Ошибка при получении объекта по id $index: ${e.message}")
            null
        }
    }

    /**
     * Добавление объекта к общему списку
     */
    fun addItem(item: T){
        val newList = adapterList.clone() as ArrayList<T>
        newList.add(item)
        onNewData(newList)
    }

    /**
     * Удаление объекта из общего списка
     */
    fun removeItem(item: T){
        val newList = adapterList.clone() as ArrayList<T>
        if (!newList.remove(item))
            log("Объект $item не был удален. Не найден в списке")
        onNewData(newList)
    }

    /**
     * Удаление объекта из общего списка
     */
    fun removeItem(index: Int){
        val newList = adapterList.clone() as ArrayList<T>
        try {
            if (newList.removeAt(index) == null)
                log("Объект с индексом $index не был удален. Не найден индекс в списке")
        }catch (e: IndexOutOfBoundsException){
            log("ОШИБКА: В общем списке ${adapterList.size} элементов. Вы пытались удалить элемент с индексом $index. Такого индекса не существует. ${e.message}")
        }

        onNewData(newList)
    }

    /**
     * Задать список элементов адаптера
     */
    fun onNewData(newData: ArrayList<T>){
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(newData, adapterList))
        diffResult.dispatchUpdatesTo(this)
        adapterList.clear()
        adapterList.addAll(newData)
    }

    /**
     * Задать список элементов адаптера
     */
    fun onNewData(newData: Array<T>){
        val array = newData.toCollection((ArrayList()))
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(array , adapterList))
        diffResult.dispatchUpdatesTo(this)
        adapterList.clear()
        adapterList.addAll(newData)
    }

    inner class MyDiffUtilCallback (
        private val newList: ArrayList<T>,
        private val oldList: ArrayList<T>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return isAreItemsTheSame(newList[newItemPosition], oldList[oldItemPosition]) ?: (newList[newItemPosition] == oldList[oldItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return try {
                isAreContentsTheSame(newList[newItemPosition], oldList[oldItemPosition]) ?: (newList[newItemPosition]!!.hashCode() == oldList[oldItemPosition]!!.hashCode())
            } catch (e: Exception) {
                false
            }
        }
    }
}