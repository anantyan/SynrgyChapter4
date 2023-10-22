package id.anantyan.synrgychapter4.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.common.emailValid
import id.anantyan.synrgychapter4.common.passwordValid
import id.anantyan.synrgychapter4.common.SharedHelper
import id.anantyan.synrgychapter4.common.SharedPreferences
import id.anantyan.synrgychapter4.data.local.entities.User
import id.anantyan.synrgychapter4.databinding.FragmentLoginBinding
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val pref: SharedHelper by lazy { SharedPreferences(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnLogin -> {
                onValidation()
            }
            binding.btnRegister -> {
                val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(destination)
            }
        }
    }

    private fun bindObserver() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success -> {
                    pref.setLogin(true)
                    pref.setUsrId(state.data?.id ?: -1L)
                    val destination = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(destination)
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onValidation() {
        validator(requireContext()) {
            mode = Mode.SINGLE
            listener = onValidationListener()
            validate(
                binding.txtInputEmail.emailValid(),
                binding.txtInputPassword.passwordValid()
            )
        }
    }

    private fun onValidationListener() = object : Validator.OnValidateListener {
        override fun onValidateFailed(errors: List<String>) { }

        override fun onValidateSuccess(values: List<String>) {
            val user = User(
                email = binding.txtEmail.text.toString(),
                password = binding.txtPassword.text.toString()
            )
            viewModel.login(user)
        }
    }

    private fun bindView() {
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        checkAuthentication()
    }

    private fun checkAuthentication() {
        if (pref.getLogin() && pref.getUsrId() != -1L) {
            val destination = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(destination)
        }
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }
}