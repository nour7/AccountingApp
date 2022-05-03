package com.example.accountingapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.accountingapp.R
import com.example.accountingapp.databinding.MainFragmentBinding
import com.example.accountingapp.services.Injector
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private val viewModel: MainViewModel by viewModels {
        Injector.provideMainViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = BudgetAdapter()
        binding.budgetList.adapter = adapter
        updateBudgetList(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun updateBudgetList(adapter: BudgetAdapter) {
       viewModel.budgetRecords.observe(viewLifecycleOwner) { records ->
           Log.v("TAG", "records $records")
           adapter.submitList(records)
       }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.budget_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_record -> {
                navigateToAddFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToAddFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_addRecordFragment)
    }

}