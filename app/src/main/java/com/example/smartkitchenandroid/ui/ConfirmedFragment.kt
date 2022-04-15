package com.example.smartkitchenandroid.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartkitchenandroid.R
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
    private var orders = emptyList<Order>()
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private val repeatPeriod: Long = 2000

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
    }

    private fun initViewModel() {
        val repository = Repository()
        val viewModelFactory = WaiterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[WaiterViewModel::class.java]
        runnable = Runnable {
            viewModel.getOrderByStatus(OrderStatus.CONFIRMED.name)
            viewModel.apiResponse.observe(requireActivity()) { response ->
                if (response.isSuccessful) {
                    if (response.data?.body()?.isNotEmpty() == true) {
                        Log.d(NewFragment.TAG, "Order : ${response.data.body()?.toString()}")
                        orders = response.data.body() as List<Order>

                        if (orders.isNotEmpty()) {
                            binding.imgPlaceholder.visibility = View.GONE
                            binding.txtPlaceholder.visibility = View.GONE
                            binding.root.gravity = Gravity.NO_GRAVITY
                            binding.rvConfirmed.visibility = View.VISIBLE
                            adapter = ConfirmedAdapter(orders)
                            binding.rvConfirmed.adapter = adapter
                        } else {
                            binding.imgPlaceholder.setImageResource(R.drawable.ic_empty_street)
                            binding.imgPlaceholder.visibility = View.VISIBLE
                            binding.rvConfirmed.visibility = View.GONE
                            binding.txtPlaceholder.visibility = View.VISIBLE
                        }
                    } else {
                        Log.d(NewFragment.TAG, "Response is empty!")
                    }
                } else {
                    if (response.exception != null) {
                        binding.root.gravity = Gravity.CENTER
                        binding.imgPlaceholder.setImageResource(R.drawable.ic_stars)
                        binding.imgPlaceholder.visibility = View.VISIBLE
                        binding.rvConfirmed.visibility = View.GONE
                        binding.txtPlaceholder.text = "No internet!"
                        binding.txtPlaceholder.visibility = View.VISIBLE
                    }
                }
            }
            handler.postDelayed(runnable, repeatPeriod)
        }
        handler.postDelayed(runnable, repeatPeriod)
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