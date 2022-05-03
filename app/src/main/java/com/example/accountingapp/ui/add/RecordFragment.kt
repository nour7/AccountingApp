package com.example.accountingapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.accountingapp.databinding.AddRecordFragmentBinding
import com.example.accountingapp.services.Injector
import com.example.accountingapp.store.database.Record
import kotlinx.coroutines.ExperimentalCoroutinesApi


class RecordFragment : Fragment() {

    val recordId: String? = null

    companion object {
        fun newInstance() = RecordFragment()
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private val viewModel: RecordViewModel by viewModels {
        Injector.provideAddRecordViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddRecordFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            if(uiState.errors.isNotEmpty()) {
                Toast.makeText(requireContext(), "Error ${uiState.errors.last()}", Toast.LENGTH_LONG).show()
            }

            if(uiState.completed) {
                findNavController().popBackStack()
            }
        }

        if (recordId != null) {
            viewModel.queryRecord(recordId)
        }

        viewModel.record.observe(viewLifecycleOwner) { record ->
            if (record != null) {
                binding.addExpenseAmount.setText(record.amount.toString())
                binding.addExpenseInfo.setText(record.description.toString())
            }
        }

        binding.addExpenseButton.setOnClickListener {
            viewModel.addOrEditRecord(viewModel.record.value, binding.addExpenseAmount.text.toString(), binding.addExpenseInfo.text.toString())
        }
        return binding.root
    }



}