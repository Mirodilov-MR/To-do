package com.example.myapplication0000.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.myapplication0000.TodosAdapter.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.R
import com.example.myapplication0000.databinding.FragmentInformationScreenBinding
import com.example.myapplication0000.room.Contacts


class InformationScreen : Fragment() {
    private var viewBinding: FragmentInformationScreenBinding? = null
    private val binding get() = viewBinding!!
    private val viewModel: ToDoViewModel by viewModels()
    private var positionNumberID = 0
    private lateinit var radioGroup: RadioGroup
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentInformationScreenBinding.inflate(inflater, container, false)
        val getObject = arguments?.getParcelable<Contacts>("position_id")
        if (getObject != null) {
            positionNumberID = getObject.id!!
            binding.txtAppbar.text = getObject.name
            binding.tvTodoDescription.text = getObject.description
            binding.tvTodoCreatedDate.text = getObject.date
            binding.tvTodoDeadline.text = getObject.deadline
            val name = arrayOf("Urgent","High", "Normal","Low")
            val img = intArrayOf(
                R.drawable.flagr,
                R.drawable.flagb,
                R.drawable.flagy,
                R.drawable.flagwh
            )
            val spinnerAdapter = SpinnerAdapter(requireContext(),img,name)
            binding.etDegree.adapter = spinnerAdapter
            val selectedDegreeIndex = name.indexOf(getObject.degree)
            if (selectedDegreeIndex != -1){
                binding.etDegree.setSelection(selectedDegreeIndex)
                }
            binding.btnOk.setOnClickListener {
                updateTodo()
             //  findNavController().navigate(R.id.action_informationScreen_to_todoList)

               findNavController().popBackStack()
            }
            radioGroup = viewBinding?.radioGroup ?: error("RadioGroup not found")
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val radioButton = group.findViewById<RadioButton>(checkedId)
                binding.open.setOnClickListener{
                    Toast.makeText(
                        requireContext(), "Open bosildi ",Toast.LENGTH_SHORT).show()
                }
                binding.development.setOnClickListener{
                    Toast.makeText(
                        requireContext(), "dev bosildi ",Toast.LENGTH_SHORT).show()
                }
                binding.upload.setOnClickListener{
                    Toast.makeText(
                        requireContext(), "upload bosildi ",Toast.LENGTH_SHORT).show()
                }
                binding.reject.setOnClickListener{
                    Toast.makeText(
                        requireContext(), "reject bosildi ",Toast.LENGTH_SHORT).show()
                }
                binding.clossed.setOnClickListener{
                    Toast.makeText(
                        requireContext(), "clossed bosildi ",Toast.LENGTH_SHORT).show()
                }

            }
        } else {
            Log.d("InformationScreen", "getObject is null")
        }

        return viewBinding?.root
    }
    private fun updateTodo() {
        val name = binding.txtAppbar.text.toString().trim()
        val description = binding.tvTodoDescription.text.toString().trim()
        val selectedDegreePosition = binding.etDegree.selectedItemPosition
        val degrees = resources.getStringArray(R.array.degrees_array)
        val degree = degrees[selectedDegreePosition]
        val date = binding.tvTodoCreatedDate.text.toString().trim()
        val deadline = binding.tvTodoDeadline.text.toString().trim()
        val data = Contacts(positionNumberID, name, description, degree, date, deadline,"")
        viewModel.updateTodos(data)
        Toast.makeText(requireContext(), "Saqlandi", Toast.LENGTH_SHORT).show()
//        binding.btnOk.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}