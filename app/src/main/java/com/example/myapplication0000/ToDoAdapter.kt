package com.example.myapplication0000

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication0000.databinding.TodosLayoutBinding
import com.example.myapplication0000.room.TodoDatabase
import com.example.myapplication0000.room.Contacts

class TodosAdapter(val context: Context, private val list: List<Contacts>) :
    RecyclerView.Adapter<TodosAdapter.ViewHolder>() {
    private lateinit var listener: OnUserClickedListener

    class ViewHolder(val binding: TodosLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val dao = TodoDatabase.getDatabaseInstance(context).todosDao()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodosLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = list[position]
        holder.binding.contactName.text = todo.name
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("position_id", todo)
            holder.itemView.findNavController()
                .navigate(R.id.action_todoList_to_informationScreen, bundle)
        }
//        holder.binding.btnDelete.setOnClickListener {
//            dao.delete(list[position])
//            notifyItemRemoved(position)
//        }
//        holder.binding.btnEdit.setOnClickListener {
//            updateMovie(movie)
//            val bundle = Bundle()
//            bundle.putParcelable("position_id", movie)
//            holder.itemView.findNavController()
//                .navigate(R.id.action_mainScreen_to_editScreen, bundle)
//            true
//        }
    }
//    private fun deleteTodo(todo: Contacts, position: Int) {
//        dao.delete(todo)
//        notifyItemRemoved(position)}

//    private fun editTodo(todo: Contacts) {
//        dao.update(todo)
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnUserClickedListener(listener: OnUserClickedListener) {
        this.listener = listener
    }

    interface OnUserClickedListener {
        fun onUserClicked(position: Int)
    }

    class SpinnerAdapter(
        applicationContext: Context,
        private val flag: IntArray,
        private val priority: Array<String>
    ) : BaseAdapter() {
        private val inflater = LayoutInflater.from(applicationContext)
        override fun getCount(): Int = flag.size
        override fun getItem(position: Int): String = ""
        override fun getItemId(position: Int): Long = 0L

        @SuppressLint("ViewHolder", "InflateParams")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val cv = inflater.inflate(R.layout.layout_item_spinner, null)
            val imgFlag = cv.findViewById<ImageView>(R.id.imgFlag)
            val tvPriority = cv.findViewById<TextView>(R.id.tvPriority)
            imgFlag.setImageResource(flag[position])
            tvPriority.text = priority[position]
            return cv
        }
    }
}
