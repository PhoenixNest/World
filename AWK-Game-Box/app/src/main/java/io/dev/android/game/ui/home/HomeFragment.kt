package io.dev.android.game.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.dev.android.game.databinding.FragmentHomeBinding
import io.dev.android.game.ui.home.adapter.HomeRVAdapter
import io.dev.android.game.ui.home.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    companion object {
        const val TAG = "HomeFragment"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Avoid OOM
        _binding = null
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        initUi()
    }

    /* ======================== Ui ======================== */

    private fun initUi() {
        setupRV()
    }

    private fun setupRV() {
        binding.recyclerViewHomeContainer.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply { orientation = RecyclerView.HORIZONTAL }
            adapter = HomeRVAdapter().apply { setData(emptyList()) }
        }
    }
}