package com.example.assignment3

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)

        // Lấy ViewModel với Factory vì TodoViewModel kế thừa AndroidViewModel
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(TodoViewModel::class.java)

        adapter = TodoAdapter(
            onClick = { todo -> showEdit(todo) },
            onCheckChanged = { todo, isChecked ->
                // Khi checkbox được thay đổi, cập nhật trạng thái "isDone" cho todo
                // Dùng copy() của data class để tạo bản sao mới với trạng thái được cập nhật
                viewModel.update(todo.copy(isDone = isChecked))
            }
        )


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Quan sát dữ liệu LiveData từ ViewModel
        viewModel.allTodos.observe(viewLifecycleOwner) { todos ->
            adapter.setItems(todos)
        }

        // Thiết lập swipe-to-delete với ItemTouchHelper
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Không hỗ trợ di chuyển (drag & drop)
                return false
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                if (dX < 0) { // Vuốt sang trái
                    // Vẽ nền cam
                    val backgroundPaint = Paint().apply {
                        color = Color.parseColor("#FFA500") // màu cam
                    }
                    canvas.drawRect(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat(),
                        backgroundPaint
                    )

                    // Vẽ chữ "Delete" màu trắng
                    val textPaint = Paint().apply {
                        color = Color.WHITE
                        textSize = 50f
                        isAntiAlias = true
                    }
                    val text = "Delete"
                    val textWidth = textPaint.measureText(text)
                    // Vị trí chữ cách mép phải 16dp và căn giữa theo chiều dọc
                    val textX = itemView.right - textWidth - 16f
                    val textY = itemView.top + (itemView.height / 2f) + (textPaint.textSize / 2f) - 8f
                    canvas.drawText(text, textX, textY, textPaint)
                }
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val todoToDelete = adapter.getItem(position)
                viewModel.delete(todoToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddTodoActivity::class.java))
        }

        return view
    }

    private fun showEdit(todo: Todo) {
        val intent = Intent(requireContext(), AddTodoActivity::class.java).apply {
            putExtra("todo_id", todo.id)
            putExtra("todo_title", todo.title)
            putExtra("todo_description", todo.description)
            putExtra("todo_done", todo.isDone)
        }
        startActivity(intent)
    }
}
