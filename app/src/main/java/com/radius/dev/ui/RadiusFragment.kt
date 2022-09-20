package com.radius.dev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.chip.Chip
import com.radius.dev.data.model.Option
import com.radius.dev.databinding.FragmentRadiusBinding
import com.radius.dev.ui.FacilityAdapter
import com.radius.dev.ui.OptionAdapter
import com.radius.dev.ui.RadiusUiState
import com.radius.dev.ui.RadiusViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class RadiusFragment : Fragment() {

    val viewModel: RadiusViewModel by viewModels()
    private var _binding: FragmentRadiusBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRadiusBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDeleteSelectedItems.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state: RadiusUiState ->
                        when (state) {
                            is RadiusUiState.Error -> {

                            }
                            is RadiusUiState.LoadingFromApi -> {}
                            is RadiusUiState.Init -> {}
                            is RadiusUiState.RadiusData -> {
                                val concatAdapter = ConcatAdapter(

                                )
                                state.items.map {
                                    val facilityAdapter = FacilityAdapter(it)
                                    concatAdapter.addAdapter(facilityAdapter)
                                    val optionsAdapter = OptionAdapter(it.options,
                                        object : OptionAdapter.OptionClickListener {
                                            override fun onOptionClick(option: Option) {
                                                val isValid = viewModel.isSelectionValid(option)
                                                if (isValid.first) {
                                                    addChips(option)
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        isValid.second,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }

                                            }

                                        })
                                    concatAdapter.addAdapter(optionsAdapter)
                                }


                                binding.rvRadius.adapter = concatAdapter

                            }
                        }
                    }

                }
            }
        }

        binding.btnDeleteSelectedItems.setOnClickListener {
            viewModel.clearSelections()
            binding.cgSelectedItems.removeAllViews()
            binding.btnDeleteSelectedItems.visibility = View.GONE
        }


    }

    fun addChips(option: Option) {
        binding.btnDeleteSelectedItems.visibility = View.VISIBLE
        Chip(context).apply {

            tag = option.id
            // val data = EmojiCompat.get().process(  interest.description)
            text = option.name

            isClickable = false
            //   setChipBackgroundColorResource(R.color.btn_color_chip)
            //setTextAppearanceResource(R.style.PostUsername)
            binding.cgSelectedItems.addView(this)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}