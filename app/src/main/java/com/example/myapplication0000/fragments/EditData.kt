package com.example.myapplication0000.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.databinding.FragmentEditDataBinding
import com.example.myapplication0000.room.Contacts
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

class EditData : Fragment() {

    private var viewBinding: FragmentEditDataBinding? = null
    private val binding get() = viewBinding!!
    private val viewModel: ToDoViewModel by viewModels()
    private var cal = Calendar.getInstance()
    private var positionNumberID = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        binding.etDate.text = currentDate
        super.onViewCreated(view, savedInstanceState)

        binding.etDeadline.setOnClickListener {
            showDatePicker()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentEditDataBinding.inflate(inflater, container, false)
        val getObject = arguments?.getParcelable<Contacts>("position_id")
        println("getObject =  $getObject")
        positionNumberID = getObject?.id!!

        // Set current contact data in EditText fields
        binding.etName.setText(getObject.name)
        binding.etDesciption.setText(getObject.description)
        binding.etDate.text = getObject.date
        binding.etDeadline.text = getObject.deadline
        val degrees = arrayOf("Urgent", "High", "Normal", "Low")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, degrees)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.etDegree.adapter = adapter

        // Set the selected value of the Spinner
        val selectedDegreeIndex = degrees.indexOf(getObject.degree)
        if (selectedDegreeIndex != -1) {
            binding.etDegree.setSelection(selectedDegreeIndex)
        }
        binding.save.setOnClickListener {
            createTodo()
        }
        return viewBinding!!.root
    }

    private fun showDatePicker() {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, day: Int ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                updateDeadlineInView()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun updateDeadlineInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.etDeadline.text = sdf.format(cal.time)
    }

    private fun createTodo() {
        val name = binding.etName.text.toString().trim()
        val description = binding.etDesciption.text.toString().trim()
        val degree = binding.etDegree.selectedItem.toString().trim()
        val date = binding.etDate.text.toString().trim()
        val deadline = binding.etDeadline.text.toString().trim()

        if (name.isEmpty()) {
            binding.etName.error = "Name cannot be empty"
            return
        }
        if (description.isEmpty()) {
            binding.etDesciption.error = "Description cannot be empty"
            return
        }
        if (date.isEmpty()) {
            binding.etDate.error = "Date cannot be empty"
            return
        }
        if (deadline.isEmpty()) {
            binding.etDeadline.error = "Deadline cannot be empty"
            return
        }
        val data = Contacts(positionNumberID, name, description, degree, date, deadline)
        viewModel.updateTodos(data)
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
