package com.example.customproject.ui.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.databinding.FragmentTransactionBinding
import com.example.customproject.ui.transaction_type.TransactionTypeFragmentDirections

private const val ARG_OBJECT = "object"
class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transactionViewModel =
            ViewModelProvider(this)[TransactionViewModel::class.java]
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recycleview:RecyclerView= binding.recyclerview
        val notification= binding.notification
        val progressBar:ProgressBar = binding.progressBar
        notification.text="No group has been created"
        var adapter:CustomAdapter
        recycleview.layoutManager = LinearLayoutManager(null,LinearLayoutManager.VERTICAL ,false)
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            transactionViewModel.getallTag(progressBar,getInt(ARG_OBJECT)).observe(viewLifecycleOwner) { it ->
                if (it.isNotEmpty()) {
                    notification.visibility = View.INVISIBLE
                    adapter = CustomAdapter(it)
                    if (getInt(ARG_OBJECT) == 0) {
                        adapter.onItemClick = {
                            val action =
                                TransactionTypeFragmentDirections.actionNavigationTransactionTypeToNavigationTransactionlist2(
                                    "Income", it
                                )
                            Navigation.findNavController(root).navigate(action)
                        }
                    } else {
                        adapter.onItemClick = {
                            val action =
                                TransactionTypeFragmentDirections.actionNavigationTransactionTypeToNavigationTransactionlist2(
                                    "Spending", it
                                )
                            Navigation.findNavController(root).navigate(action)
                        }
                    }
                    recycleview.adapter = adapter
                }
            }
        }

        return root
    }


}