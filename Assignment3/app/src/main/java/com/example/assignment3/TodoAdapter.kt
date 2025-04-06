package com.example.assignment3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val onClick: (Todo) -> Unit,
    private val onCheckChanged: (Todo, Boolean) -> Unit // Callback xử lý sự kiện checkbox
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var items = listOf<Todo>()

    // Cập nhật danh sách dữ liệu và thông báo cho adapter
    fun setItems(newItems: List<Todo>) {
        items = newItems
        notifyDataSetChanged()
    }

    // Hàm lấy đối tượng Todo theo vị trí
    fun getItem(position: Int): Todo = items[position]

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.textTitle)
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkboxDone)

        fun bind(todo: Todo) {
            title.text = todo.title

            // Đảm bảo reset listener trước khi gán trạng thái mới
            checkbox.setOnCheckedChangeListener(null)
            checkbox.isChecked = todo.isDone

            // Bật tương tác checkbox: khi thay đổi trạng thái, gọi callback onCheckChanged
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged(todo, isChecked)
            }

            // Sự kiện click cho cả item (có thể dùng để mở màn hình chỉnh sửa)
            itemView.setOnClickListener {
                onClick(todo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
