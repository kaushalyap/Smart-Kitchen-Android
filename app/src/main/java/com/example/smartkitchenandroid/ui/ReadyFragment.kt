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
import com.example.smartkitchenandroid.adapters.OnItemClickListener
import com.example.smartkitchenandroid.adapters.ReadyAdapter
import com.example.smartkitchenandroid.databinding.FragmentReadyBinding
import com.example.smartkitchenandroid.models.Order
import com.example.smartkitchenandroid.models.OrderStatus
import com.example.smartkitchenandroid.repository.Repository
import com.example.smartkitchenandroid.viewmodels.WaiterViewModel
import com.example.smartkitchenandroid.viewmodels.WaiterViewModelFactory


class ReadyFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentReadyBinding? = null
    private val binding get() = _binding!!
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ReadyAdapter
    private lateinit var viewModel: WaiterViewModel
    private var orders = ArrayList<Order>()
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private val repeatPeriod: Long = 2000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadyBinding.inflate(inflater, container, false)
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

            viewModel.getOrderByStatus(OrderStatus.READY.name)

            viewModel.apiResponse.observe(requireActivity()) { response ->
                if (response.isSuccessful) {
                    if (response.data?.body()?.isNotEmpty() == true) {
                        Log.d(TAG, "Order : ${response.data.body()?.toString()}")
                        orders = response.data.body() as ArrayList<Order>
                        if (orders.isNotEmpty()) {
                            binding.imgPlaceholder.visibility = View.GONE
                            binding.txtPlaceholder.visibility = View.GONE
                            binding.root.gravity = Gravity.NO_GRAVITY
                            binding.rvReady.visibility = View.VISIBLE
                            adapter = ReadyAdapter(orders, this)
                            binding.rvReady.adapter = adapter
                        } else {
                            showPlaceholder()
                        }
                    } else
                        Log.d(TAG, "Response is empty!")

                } else {
                    if (response.exception != null) {
                        binding.root.gravity = Gravity.CENTER
                        binding.imgPlaceholder.setImageResource(R.drawable.ic_stars)
                        binding.txtPlaceholder.text = "No internet!"
                        showPlaceholder()
                    }
                }
            }
            handler.postDelayed(runnable, repeatPeriod)
        }
        handler.postDelayed(runnable, repeatPeriod)
    }

    private fun showPlaceholder() {
        binding.rvReady.visibility = View.GONE
        binding.imgPlaceholder.visibility = View.VISIBLE
        binding.txtPlaceholder.visibility = View.VISIBLE
    }

    override fun onItemClick(position: Int) {
        val order = orders[position]
        order.status = OrderStatus.CONFIRMED
        viewModel.updateOrderStatus(order.id.toInt(), order)

        viewModel.statusCode.observe(requireActivity()) { response ->
            if (response.isSuccessful) {
                orders.removeAt(position)
                adapter.notifyItemRemoved(position)

                if (orders.isEmpty()) {
                    binding.root.gravity = Gravity.CENTER
                    binding.imgPlaceholder.setImageResource(R.drawable.ic_empty_street)
                    showPlaceholder()
                }
            } else {
                Log.d(NewFragment.TAG, response.exception?.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "ReadyFragment"
        fun newInstance(): ReadyFragment = ReadyFragment()

    }
}