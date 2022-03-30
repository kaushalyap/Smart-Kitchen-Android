package com.example.smartkitchenandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartkitchenandroid.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

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
                /*val user:User = Api().fetchUser(email, password)
                if (user.role == Roles.WAITER)
                    findNavController().navigate(R.id.action_signIn_to_waiter)
                else
                    findNavController().navigate(R.id.action_signIn_to_kitchenCoordinator)*/
            }
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