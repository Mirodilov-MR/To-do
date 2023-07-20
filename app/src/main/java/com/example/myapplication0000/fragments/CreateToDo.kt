package com.example.myapplication0000.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.myapplication0000.TodosAdapter.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.R
import com.example.myapplication0000.databinding.FragmentCreateTodoBinding
import com.example.myapplication0000.room.Contacts
import java.text.SimpleDateFormat
import java.util.*

class CreateToDo : Fragment() {
    private lateinit var binding: FragmentCreateTodoBinding
    private val viewModel: ToDoViewModel by viewModels()
    private var cal = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        binding.etDate.text = currentDate
        val name = arrayOf(
            "Urgent", "High", "Normal", "Low"
        )
        val img = intArrayOf(
            R.drawable.flagr,
            R.drawable.flagb,
            R.drawable.flagy,
            R.drawable.flagwh
        )
        val spinnerAdapter = SpinnerAdapter(requireContext(), img, name)
        binding.etDegree.adapter = spinnerAdapter
        binding.save.setOnClickListener {
            createTodos()
        }

        binding.etDeadline.setOnClickListener {
            showDatePicker()
        }
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
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.etDeadline.text = sdf.format(cal.time)
    }

    private fun createTodos() {
        val name = binding.etName.text.toString().trim()
        val description = binding.etDesciption.text.toString().trim()
        val selectedDegreePosition = binding.etDegree.selectedItemPosition

        val degrees = resources.getStringArray(R.array.degrees_array)

        if (name.isEmpty()) {
            binding.etName.error = "Name cannot be empty"
            return
        }
        if (description.isEmpty()) {
            binding.etDesciption.error = "Description cannot be empty"
            return
        }
        if (selectedDegreePosition == -1) {
            Toast.makeText(requireContext(), "Please select a degree", Toast.LENGTH_SHORT).show()
            return
        }
        val degree = degrees[selectedDegreePosition]
        val date = binding.etDate.text.toString().trim()
        val deadline = binding.etDeadline.text.toString().trim()
        val data = Contacts(null, name, description, degree, date, deadline,"open")
        viewModel.addTodos(data)

        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }
}
