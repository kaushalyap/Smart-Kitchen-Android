package com.example.smartkitchenandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartkitchenandroid.adapters.ConfirmedAdapter
import com.example.smartkitchenandroid.databinding.FragmentTodoBinding
import com.example.smartkitchenandroid.models.Order

class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ConfirmedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        setupRV()
        return binding.root
    }

    private fun setupRV() {
        val orders = emptyArray<Order>()
        linearLayoutManager = LinearLayoutManager(context)
        binding.rvTodo.layoutManager = linearLayoutManager
        adapter = ConfirmedAdapter(orders)
        binding.rvTodo.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "TodoFragment"
    }
}