package com.example.smartkitchenandroid.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartkitchenandroid.R
import com.example.smartkitchenandroid.adapters.ViewPagerAdapter
import com.example.smartkitchenandroid.databinding.FragmentWaiterBinding
import com.google.android.material.tabs.TabLayoutMediator


class WaiterFragment : Fragment() {

    private val tabs = arrayOf("New", "Confirmed", "Ready")
    private var _binding: FragmentWaiterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaiterBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        val fragmentList = arrayListOf(
            NewFragment.newInstance(),
            ConfirmedFragment.newInstance(),
            ReadyFragment.newInstance()
        )
        binding.pager.adapter = ViewPagerAdapter(requireActivity(), fragmentList)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> findNavController().navigate(R.id.action_global_about)
            R.id.sign_out -> signOut()
        }
        return true
    }

    private fun signOut() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.pref_already_signed_in), false)
            putString(getString(R.string.pref_role), "")
            apply()
        }
        findNavController().navigate(R.id.action_global_signIn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "WaiterFragment"
    }
}