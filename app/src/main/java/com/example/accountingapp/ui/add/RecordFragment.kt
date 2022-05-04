package com.example.accountingapp.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.accountingapp.databinding.FragmentRecordsBinding
import com.example.accountingapp.services.Injector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


class RecordFragment : Fragment() {

    private val args: RecordFragmentArgs by navArgs()

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private val viewModel: RecordViewModel by viewModels {
        Injector.provideAddRecordViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecordsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            if(uiState.errors.isNotEmpty()) {
                Toast.makeText(requireContext(), "Error ${uiState.errors.last()}", Toast.LENGTH_LONG).show()
            }

            if(uiState.completed) {
                findNavController().popBackStack()
            }

            binding.saveRecord.isEnabled = viewModel.uiState.value?.savedButtonEnabled ?: false
        }




        viewModel.record.observe(viewLifecycleOwner) { record ->
            if (record != null) {
                binding.recordExpenseValue.setText(record.amount.toString())
                binding.recordExpenseInfo.setText(record.description.toString())
            }
        }


        binding.saveRecord.setOnClickListener {
            viewModel.addOrEditRecord(viewModel.record.value, binding.recordExpenseValue.text.toString(), binding.recordExpenseInfo.text.toString())
        }

        binding.recordExpenseValue.doOnTextChanged { text, _, _, _ ->
            viewModel.updateAmount(text)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (args.recordId != null) {
            viewModel.queryRecord(args.recordId ?: "")
        }
    }



}