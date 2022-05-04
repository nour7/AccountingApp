package com.example.accountingapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.accountingapp.R
import com.example.accountingapp.databinding.MainFragmentBinding
import com.example.accountingapp.services.Injector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

class MainFragment : Fragment(), OnBudgetItemClickListener {

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private val viewModel: MainViewModel by viewModels {
        Injector.provideMainViewModelFactory(requireContext())
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = BudgetAdapter(this)
        binding.budgetList.adapter = adapter
        updateBudgetList(adapter)

        viewModel.sumAmount.observe(viewLifecycleOwner) {
            binding.sumTextView.text = "€${it}"
        }

        setHasOptionsMenu(true)
        return binding.root
    }



    private fun updateBudgetList(adapter: BudgetAdapter) {
       viewModel.budgetRecords.observe(viewLifecycleOwner) { records ->
           adapter.submitList(records)
       }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.budget_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_record -> {
                navigateToRecordFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToRecordFragment() {
        val action = MainFragmentDirections.actionMainFragment2ToRecordFragment3(null)
        findNavController().navigate(action)
    }

    override fun onItemClick(id: UUID?) {
       val action = MainFragmentDirections.actionMainFragment2ToRecordFragment3(id.toString())
        findNavController().navigate(action)
    }

}