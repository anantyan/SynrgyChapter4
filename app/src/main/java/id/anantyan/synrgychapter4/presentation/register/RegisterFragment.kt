package id.anantyan.synrgychapter4.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.common.confirmPasswordValid
import id.anantyan.synrgychapter4.common.emailValid
import id.anantyan.synrgychapter4.common.generalValid
import id.anantyan.synrgychapter4.common.passwordValid
import id.anantyan.synrgychapter4.common.usernameValid
import id.anantyan.synrgychapter4.data.local.entities.User
import id.anantyan.synrgychapter4.databinding.FragmentRegisterBinding
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

class RegisterFragment : Fragment(), View.OnClickListener {

    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
            binding.btnRegister -> {
                onValidation()
            }
            binding.btnLogin -> {
                findNavController().navigateUp()
            }
        }
    }

    private fun bindObserver() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success -> {
                    findNavController().navigateUp()
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun bindView() {
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    private fun onValidation() {
        validator(requireContext()) {
            mode = Mode.SINGLE
            listener = onValidationListener()
            validate(
                binding.txtInputName.generalValid(),
                binding.txtInputUsername.usernameValid(),
                binding.txtInputEmail.emailValid(),
                binding.txtInputPassword.passwordValid(),
                binding.txtInputRePassword.confirmPasswordValid(binding.txtPassword)
            )
        }
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    private fun onValidationListener() = object : Validator.OnValidateListener {
        override fun onValidateFailed(errors: List<String>) { }

        override fun onValidateSuccess(values: List<String>) {
            val user = User(
                name = binding.txtName.text.toString(),
                username = binding.txtUsername.text.toString(),
                email = binding.txtEmail.text.toString(),
                password = binding.txtRePassword.text.toString()
            )
            viewModel.register(user)
        }
    }
}