package com.example.customproject.ui.transaction_list


import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.databinding.FragmentTransactionsListBinding
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.example.customproject.ui.bottomfragment.CustomBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog


class TransactionsListFragmant : Fragment() {

    private var _binding: FragmentTransactionsListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsListBinding.inflate(inflater, container, false)
        val transactionsListViewModel = ViewModelProvider(this)[TransactionsListViewModel::class.java]
        val recycleview: RecyclerView = binding.recyclerview
        var adapter: CustomAdapter
        val bundle = arguments
        if (bundle == null) {
            Log.e("404", "Fragment did not receive traveler information")
        }
        val args = bundle?.let { TransactionsListFragmantArgs.fromBundle(it) }
        val labelName = args?.labelName
        val transType = args?.transType
        val fragmentManager = activity?.supportFragmentManager
        val list: MutableList<Transaction> = mutableListOf()
        recycleview.layoutManager = LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false)
        if (transType == "Income") {
            if (labelName != null)
                transactionsListViewModel.getallTransaction(TransactionType.Income, args.labelName).observe(
                    viewLifecycleOwner
                ) { transactions ->
                    if (transactions.isNotEmpty()) {
                        adapter = CustomAdapter(transactions)
                        adapter.onItemClick = {
                            it.id?.let { it1 ->
                                CustomBottomSheetDialogFragment(it1,TransactionType.Income,args.labelName).apply {
                                    if (fragmentManager != null) {
                                        show(fragmentManager,CustomBottomSheetDialogFragment.TAG)
                                    }
                                }
                            }
                        }
                        recycleview.adapter = adapter
                        list.clear()
                        transactions.forEach {
                            list.add(it)
                        }
                    }
                }
        } else {
            if (labelName != null) {
                transactionsListViewModel.getallTransaction(
                    TransactionType.Spending,
                    args.labelName
                ).observe(
                    viewLifecycleOwner
                ) { transactions ->
                    if (transactions.isNotEmpty()) {
                        adapter = CustomAdapter(transactions)
                        adapter.onItemClick = {
                            it.id?.let { it1 ->
                                CustomBottomSheetDialogFragment(it1,TransactionType.Spending,args.labelName).apply {
                                    if (fragmentManager != null) {
                                        show(fragmentManager,CustomBottomSheetDialogFragment.TAG)
                                    }
                                }
                            }
                        }
                        recycleview.adapter = adapter
                        list.clear()
                        transactions.forEach {
                            list.add(it)
                        }
                    }
                }
            }
        }

        if (labelName != null) {
            transactionsListViewModel.getallTransaction(TransactionType.Income, args.labelName).observe(
                viewLifecycleOwner
            ) { transactions ->
                if (transactions.isNotEmpty()) {
                    adapter = CustomAdapter(transactions)
                    adapter.onItemClick = {
                        it.id?.let { it1 ->
                            CustomBottomSheetDialogFragment(it1,TransactionType.Income,args.labelName).apply {
                                if (fragmentManager != null) {
                                    show(fragmentManager,CustomBottomSheetDialogFragment.TAG)
                                }
                            }
                        }
                    }
                    recycleview.adapter = adapter
                    list.clear()
                    transactions.forEach {
                        list.add(it)
                    }
                }
            }
        }


        adapter = CustomAdapter(list)
        recycleview.adapter = adapter
        val actionBar = activity?.actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val search: SearchView = binding.search
        val searchManager = context?.getSystemService(SEARCH_SERVICE) as SearchManager
        search.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        adapter.Translist = transactionsListViewModel.transactionlist.value!!
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.filterList(newText)
                    }
                }
                return true
            }

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("Resume","Resume")
    }

}