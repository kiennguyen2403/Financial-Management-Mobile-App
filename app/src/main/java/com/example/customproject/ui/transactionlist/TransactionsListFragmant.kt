package com.example.customproject.ui.transactionlist

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.databinding.FragmentTransactionsListBinding
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import java.io.Console

class TransactionsListFragmant : Fragment() {

    private lateinit var viewModel: TransactionsListViewModel
    private var _binding: FragmentTransactionsListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentTransactionsListBinding.inflate(inflater, container, false)
        val transactionsListViewModel =   ViewModelProvider(this).get(TransactionsListViewModel::class.java)
        val recycleview: RecyclerView = binding.recyclerview
        var adapter: CustomAdapter
        val bundle= arguments
        if (bundle == null) {
            Log.e("404", "Fragment did not receive traveler information")
        }
        val args = bundle?.let { TransactionsListFragmantArgs.fromBundle(it) }
        val labelName = args?.labelName
        val transType =args?.transType

        var list:MutableList<Transaction> = mutableListOf()
        recycleview.layoutManager = LinearLayoutManager(null, LinearLayoutManager.VERTICAL ,false)
        if (transType == "Income") {
            if (labelName != null) {
                transactionsListViewModel.getallTransaction(TransactionType.Income,args.labelName.toString()).observe(viewLifecycleOwner
                ) {
                    if (it.size > 0) {
                        adapter = CustomAdapter(it)
                        recycleview.adapter = adapter
                        list.clear()
                        it.forEach {
                            list.add(it)
                        }
                    }
                }
            }
        }
        else{
            if (labelName != null) {
                transactionsListViewModel.getallTransaction(TransactionType.Spending,
                    args.labelName
                ).observe(viewLifecycleOwner
                ) {
                    if (it.size > 0) {
                        adapter = CustomAdapter(it)
                        recycleview.adapter = adapter
                        list.clear()
                        it.forEach {
                            list.add(it)
                        }
                    }
                }
            }
        }


        if (labelName != null) {
            transactionsListViewModel.getallTransaction(TransactionType.Income, args.labelName).observe(viewLifecycleOwner
            ) {
                if (it.size > 0) {
                    adapter = CustomAdapter(it)
                    recycleview.adapter = adapter
                    list.clear()
                    it.forEach {
                        list.add(it)
                    }
                }
            }
        }
        adapter = CustomAdapter(list)
        recycleview.adapter = adapter
        val actionBar = activity?.actionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)

        val search :SearchView = binding.search
        val searchManager = context?.getSystemService(SEARCH_SERVICE) as SearchManager
        search.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.length== 0){
                        adapter.Translist= transactionsListViewModel.transactionlist.value!!
                        adapter.notifyDataSetChanged()
                    }
                    else{
                        adapter.filterList(newText)
                    }
                }
                return true
            }

        })


        val root:View =binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}