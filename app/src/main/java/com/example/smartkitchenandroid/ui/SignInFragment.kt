package com.example.smartkitchenandroid.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartkitchenandroid.R
import com.example.smartkitchenandroid.databinding.FragmentSignInBinding
import com.example.smartkitchenandroid.models.Roles
import com.example.smartkitchenandroid.models.User
import com.example.smartkitchenandroid.repository.Repository
import com.example.smartkitchenandroid.viewmodels.SignInViewModel
import com.example.smartkitchenandroid.viewmodels.SignInViewModelFactory
import com.google.android.material.snackbar.Snackbar


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            if (email.isEmpty() or password.isEmpty()) {
                val contextView = binding.root
                Snackbar.make(
                    contextView,
                    "Email or password cannot be empty!",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                initViewModel(email, password)
            }
        }
    }

    private fun initViewModel(email: String, password: String) {
        val repository = Repository()
        val viewModelFactory = SignInViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SignInViewModel::class.java]
        val user = User(null, email, password, null)
        viewModel.getUser(user)
        viewModel.apiResponse.observe(requireActivity()) { response ->
            if (response.isSuccessful) {
                if (response.body()?.isNotEmpty() == true) {
                    Log.d(TAG, "Order ${response.body()?.get(0)?.role?.toString()}")
                    val role = response.body()?.get(0)?.role?.toString()

                    if (role == Roles.WAITER.name) {
                        findNavController().navigate(R.id.action_signIn_to_waiter)
                        saveAlreadySignedInPref(Roles.WAITER)
                    } else {
                        findNavController().navigate(R.id.action_signIn_to_kitchenCoordinator)
                        saveAlreadySignedInPref(Roles.KITCHEN_COORDINATOR)
                    }
                } else
                    Log.d(TAG, "Response is empty!")
            } else
                Log.d(TAG, "Error : ${response.code()}")
        }
    }

    private fun saveAlreadySignedInPref(role: Roles) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.pref_already_signed_in), true)
            putString(getString(R.string.pref_role), role.name)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "SignInFragment"
    }
}