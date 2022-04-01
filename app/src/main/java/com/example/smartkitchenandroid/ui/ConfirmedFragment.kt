package com.example.smartkitchenandroid.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartkitchenandroid.adapters.ConfirmedAdapter
import com.example.smartkitchenandroid.databinding.FragmentConfirmedBinding
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.OrderStatus
import com.example.smartkitchenandroid.repository.Repository
import com.example.smartkitchenandroid.viewmodels.WaiterViewModel
import com.example.smartkitchenandroid.viewmodels.WaiterViewModelFactory


class ConfirmedFragment : Fragment() {

    private var _binding: FragmentConfirmedBinding? = null
    private val binding get() = _binding!!
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ConfirmedAdapter
    private lateinit var viewModel: WaiterViewModel
    private var orders = emptyArray<Order>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmedBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initViewModel()
        setupRV()
    }

    private fun setupRV() {
        val orders = emptyArray<Order>()
        linearLayoutManager = LinearLayoutManager(context)
        binding.rvConfirmed.layoutManager = linearLayoutManager
        adapter = ConfirmedAdapter(orders)
        binding.rvConfirmed.adapter = adapter
    }

    private fun initViewModel() {
        val repository = Repository()
        val viewModelFactory = WaiterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WaiterViewModel::class.java]
        viewModel.getOrderByStatus(OrderStatus.CONFIRMED.name)
        viewModel.apiResponse.observe(requireActivity()) { response ->
            if (response.isSuccessful) {
                if (response.body()?.isNotEmpty() == true) {
                    Log.d(TAG, "Order : ${response.body()?.get(0)?.order.toString()}")
                    orders = response.body() as Array<Order>
                } else
                    Log.d(TAG, "Response is empty!")
            } else
                Log.d(TAG, "Error : ${response.code()}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val TAG: String = "ConfirmedFragment"
        fun newInstance(): ConfirmedFragment = ConfirmedFragment()

    }
}