package com.example.myapplication0000.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.R
import com.example.myapplication0000.databinding.FragmentInformationScreenBinding
import com.example.myapplication0000.room.Contacts


class InformationScreen : Fragment() {
    private var viewBinding: FragmentInformationScreenBinding? = null
    private val binding get() = viewBinding!!
    private var positionNumberID = 0
    private lateinit var radioGroup: RadioGroup
    private val priorityFlagMap = mapOf(
        "Urgent" to R.drawable.flagr,
        "High" to R.drawable.flagb,
        "Normal" to R.drawable.flagy,
        "Low" to R.drawable.flagwh
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentInformationScreenBinding.inflate(inflater, container, false)
        val title = arguments?.getString("title")
        val getObject = arguments?.getParcelable<Contacts>("childObj")
        if (getObject != null) {
            positionNumberID = getObject.id!!
            binding.txtAppbar.text = getObject.name
            binding.tvTodoDescription.text = getObject.description
            binding.tvTodoPriority.text = getObject.degree
            val flagDrawableId = priorityFlagMap[getObject.degree]
            if (flagDrawableId != null) {
                binding.imgFlag.setImageResource(flagDrawableId)
            }
            binding.tvTodoCreatedDate.text = getObject.date
            binding.tvTodoDeadline.text = getObject.deadline
            binding.btnOk.setOnClickListener {
                findNavController().popBackStack()
            }
            radioGroup = viewBinding?.radioGroup ?: error("RadioGroup not found")
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val radioButton = group.findViewById<RadioButton>(checkedId)
                Toast.makeText(
                    requireContext(), "Selected Radio Button is: " + radioButton.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Log.d("InformationScreen", "getObject is null")
        }



        return viewBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}