package id.anantyan.synrgychapter4.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.synrgychapter4.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileFragment : Fragment(), View.OnClickListener {

    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val adapter: ProfileAdapter by lazy { ProfileAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    private fun bindObserver() {
        viewModel.getTheme.onEach {
            binding.btnTheme.isChecked = it
        }.flowWithLifecycle(viewLifecycleOwner.lifecycle).launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getUsers.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.rvProfile.adapter = adapter
        }

        viewModel.getUsrId.onEach {
            viewModel.getUsers(it)
        }.flowWithLifecycle(viewLifecycleOwner.lifecycle).launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun bindView() {
        binding.btnTheme.setOnCheckedChangeListener { _, bool -> viewModel.setTheme(bool) }
        binding.btnLogout.setOnClickListener(this)

        binding.rvProfile.setHasFixedSize(true)
        binding.rvProfile.itemAnimator = DefaultItemAnimator()
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfile.isNestedScrollingEnabled = true

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onClick(p0: View?) {
        viewModel.setLogin(false)
        viewModel.setUsrId(-1)
        val destination = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        findNavController().navigate(destination)
    }
}