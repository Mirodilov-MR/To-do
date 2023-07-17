package com.example.myapplication0000.fragments

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication0000.R
import com.example.myapplication0000.TodosAdapter
import com.example.myapplication0000.databinding.FragmentTodoListBinding
class TodoListFragment : Fragment(R.layout.fragment_todo_list),
    TodosAdapter.OnUserClickedListener {
    private lateinit var binding: FragmentTodoListBinding
    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var rvAdapter: TodosAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodoListBinding.bind(view)

        binding.sortBtn.setOnClickListener {
            showPopupMenu()
        }

        viewModel.getAllTodos().observe(viewLifecycleOwner) { list ->
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            rvAdapter = TodosAdapter(requireContext(), list)
            binding.recyclerView.adapter = rvAdapter
            rvAdapter.setOnUserClickedListener(this)
        }
    }

    override fun onUserClicked(position: Int) {
        showPopupMenu()
    }

    private fun showPopupMenu() {
        val context = requireContext()
        val popupMenu = PopupMenu(context, binding.sortBtn)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_create_date -> {
                    Toast.makeText(context, "Clicked sort by created date", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.sort_deadline_date -> {
                    Toast.makeText(context, "Clicked sort by deadline date", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.sort_priority -> {
                    Toast.makeText(context,"clicked sort by priority", Toast.LENGTH_SHORT).show()
                true
                }

                else -> false
            }
        }

        popupMenu.show()
    }
}