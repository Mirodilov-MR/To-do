package com.example.myapplication0000.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.R
import com.example.myapplication0000.databinding.FragmentMainscreenBinding

class MainScreen : Fragment() {
    private var _binding: FragmentMainscreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainscreenBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.createTodo)
        }
        binding.btnList.setOnClickListener {
            findNavController().navigate(R.id.todoList)
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
