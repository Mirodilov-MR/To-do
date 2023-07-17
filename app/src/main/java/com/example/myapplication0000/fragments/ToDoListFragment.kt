package com.example.myapplication0000.fragments

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication0000.R
import com.example.myapplication0000.TodosAdapter
import com.example.myapplication0000.adapter.ExpandableAdapter
import com.example.myapplication0000.databinding.FragmentTodoListBinding
import com.example.myapplication0000.model.ParentExpandable
import com.example.myapplication0000.room.Contacts

class TodoListFragment : Fragment(R.layout.fragment_todo_list),
    TodosAdapter.OnUserClickedListener {
    private lateinit var binding: FragmentTodoListBinding
    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var rvAdapter: TodosAdapter
    private var parentList: MutableList<ParentExpandable> = ArrayList()
    private var childList: MutableList<MutableList<Contacts>> = ArrayList()
    private var openList: MutableList<Contacts> = ArrayList()
    private var developmentList: MutableList<Contacts> = ArrayList()
    private var uploadList: MutableList<Contacts> = ArrayList()
    private var rejectList: MutableList<Contacts> = ArrayList()
    private var clossingList: MutableList<Contacts> = ArrayList()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodoListBinding.bind(view)

        binding.sortBtn.setOnClickListener {
            showPopupMenu()
        }

        viewModel.getAllTodos().observe(viewLifecycleOwner) { list ->
            openList = list as MutableList<Contacts>
            initExpandableData()
        }
    }

    private fun initExpandableData() {
        parentExpandableData()
        childList.add(openList)
        childList.add(developmentList)
        childList.add(uploadList)
        childList.add(rejectList)
        childList.add(clossingList)

        val expandableAdapter = ExpandableAdapter(binding.root.context, parentList, childList)
        binding.expandableView.setAdapter(expandableAdapter)
        expandableAdapter.setOnClickChildItem(object : ExpandableAdapter.OnChildItemClickListener {
            override fun onChildItemClick(childPosition: Int, parentPosition: Int) {
                val childObject: Contacts = childList[parentPosition][childPosition]
                val bundle = Bundle()
                bundle.putString("title", parentList[parentPosition].title)
                bundle.putParcelable("childObj", childObject)

                findNavController().navigate(R.id.action_todoList_to_informationScreen, bundle)
            }
        })
    }

    private fun parentExpandableData() {
        parentList.clear()
        parentList.add(ParentExpandable("Open"))
        parentList.add(ParentExpandable("Development"))
        parentList.add(ParentExpandable("Uploading"))
        parentList.add(ParentExpandable("Reject"))
        parentList.add(ParentExpandable("Clossing"))
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
                    Toast.makeText(context, "Clicked sort by created date", Toast.LENGTH_SHORT)
                        .show()
                    true
                }

                R.id.sort_deadline_date -> {
                    Toast.makeText(context, "Clicked sort by deadline date", Toast.LENGTH_SHORT)
                        .show()
                    true
                }

                R.id.sort_priority -> {
                    Toast.makeText(context, "Clicked sort by priority", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }
}
