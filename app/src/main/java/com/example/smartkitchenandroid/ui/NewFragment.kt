package com.example.smartkitchenandroid.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartkitchenandroid.R
import com.example.smartkitchenandroid.adapters.NewAdapter
import com.example.smartkitchenandroid.databinding.FragmentNewOrderBinding
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.OrderStatus
import com.example.smartkitchenandroid.repository.Repository
import com.example.smartkitchenandroid.viewmodels.WaiterViewModel
import com.example.smartkitchenandroid.viewmodels.WaiterViewModelFactory


class NewFragment : Fragment() {

    private var _binding: FragmentNewOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: NewAdapter
    private lateinit var viewModel: WaiterViewModel
    private var orders = emptyList<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initViewModel()
        setupRV()
    }

    private fun setupRV() {
        linearLayoutManager = LinearLayoutManager(context)
        binding.rvNew.layoutManager = linearLayoutManager
    }

    private fun initViewModel() {
        val repository = Repository()
        val viewModelFactory = WaiterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WaiterViewModel::class.java]
        viewModel.getOrderByStatus(OrderStatus.NEW.name)
        viewModel.error.observe(requireActivity()) { error ->
            if (error == null) {
                viewModel.apiResponse.observe(requireActivity()) { response ->
                    if (response.isSuccessful) {
                        if (response.body()?.isNotEmpty() == true) {
                            Log.d(TAG, "Order : ${response.body()?.toString()}")
                            orders = response.body() as List<Order>
                            if (orders.isNotEmpty()) {
                                binding.root.gravity = Gravity.NO_GRAVITY
                                binding.rvNew.visibility = View.VISIBLE
                                adapter = NewAdapter(orders)
                                binding.rvNew.adapter = adapter
                            } else {
                                binding.imgPlaceholder.visibility = View.VISIBLE
                                binding.rvNew.visibility = View.GONE
                                binding.txtPlaceholder.visibility = View.VISIBLE
                            }
                        } else
                            Log.d(TAG, "Response is empty!")
                    } else
                        Log.d(SignInFragment.TAG, "Error : ${response.code()}")
                }
            } else {
                binding.rvNew.visibility = View.GONE
                binding.imgPlaceholder.setImageResource(R.drawable.ic_stars)
                binding.imgPlaceholder.visibility = View.VISIBLE

                binding.txtPlaceholder.text = "No internet!"
                binding.txtPlaceholder.visibility = View.VISIBLE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "NewFragment"
        fun newInstance(): NewFragment = NewFragment()
    }
}