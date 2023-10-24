package id.anantyan.synrgychapter4.presentation.add_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.anantyan.synrgychapter4.common.SharedHelper
import id.anantyan.synrgychapter4.common.SharedPreferences
import id.anantyan.synrgychapter4.common.generalValid
import id.anantyan.synrgychapter4.common.numericValid
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.databinding.FragmentAddProductBinding
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

class AddProductFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private val viewModel: AddProductViewModel by viewModels()
    private val pref: SharedHelper by lazy { SharedPreferences(requireContext()) }
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
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
        viewModel.addProduct.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
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
                name = binding.txtName.text.toString(),
                description = binding.txtDescription.text.toString(),
                quantity = binding.txtQuantity.text.toString().toLong(),
                price = binding.txtPrice.text.toString().toInt(),
                userId = pref.getUsrId()
            )
            viewModel.addProduct(product)
        }
    }
}