package id.anantyan.synrgychapter4.presentation.edit_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.anantyan.synrgychapter4.common.UIState
import id.anantyan.synrgychapter4.common.generalValid
import id.anantyan.synrgychapter4.common.numericValid
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.databinding.FragmentEditProductBinding
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

class EditProductFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private val viewModel: EditProductViewModel by viewModels()
    private val args: EditProductFragmentArgs by navArgs()
    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        onValidation()
    }

    private fun bindObserver() {
        viewModel.checkProduct.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success -> {
                    @Suppress("SetTextI18n")
                    binding.textView.text = "Edit Product - ${state.data?.name}"
                    binding.txtName.setText(state.data?.name)
                    binding.txtDescription.setText(state.data?.description)
                    binding.txtQuantity.setText(state.data?.quantity.toString())
                    binding.txtPrice.setText(state.data?.price.toString())
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.editProduct.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.checkProduct(args.id)
    }

    private fun bindView() {
        binding.btnSave.setOnClickListener(this)
    }

    private fun onValidation() {
        validator(requireContext()) {
            mode = Mode.SINGLE
            listener = onValidationListener()
            validate(
                binding.txtInputName.generalValid(),
                binding.txtInputDescription.generalValid(),
                binding.txtInputQuantity.generalValid(),
                binding.txtInputPrice.numericValid()
            )
        }
    }

    private fun onValidationListener() = object : Validator.OnValidateListener {
        override fun onValidateFailed(errors: List<String>) { }

        override fun onValidateSuccess(values: List<String>) {
            val product = Product(
                id = args.id,
                name = binding.txtName.text.toString(),
                description = binding.txtDescription.text.toString(),
                quantity = binding.txtQuantity.text.toString().toLong(),
                price = binding.txtPrice.text.toString().toInt(),
                userId = viewModel.getUsrId
            )
            viewModel.editProduct(product)
        }
    }
}