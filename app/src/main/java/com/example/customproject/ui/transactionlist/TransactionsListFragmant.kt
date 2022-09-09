package com.example.customproject.ui.transactionlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.databinding.FragmentTransactionsListBinding
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.example.customproject.ui.transactionlist.CustomAdapter

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
        recycleview.layoutManager = LinearLayoutManager(null, LinearLayoutManager.VERTICAL ,false)
        transactionsListViewModel.getallTransaction(TransactionType.Income).observe(viewLifecycleOwner, Observer<List<Transaction>>() {
            if (it.size>0) {
                adapter = CustomAdapter(it)
                recycleview.adapter = adapter
            }
        })

        val actionBar = activity?.actionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)


        val root:View =binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}