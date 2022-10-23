package com.example.customproject.ui.income

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.controller.NotificationController
import com.example.customproject.controller.TagController
import com.example.customproject.controller.TransactionController
import com.example.customproject.databinding.FragmentIncomeBinding
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.type.Color
import kotlin.jvm.internal.Intrinsics


class IncomeFragment : Fragment() {

    private var _binding: FragmentIncomeBinding? = null
    private val transactionController: TransactionController = TransactionController()
    private val notificationController: NotificationController = NotificationController()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val incomeViewModel =
            ViewModelProvider(this).get(IncomeViewModel::class.java)
        _binding = FragmentIncomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recycleview:RecyclerView= binding.recyclerview
        val notification= binding.notification
        notification.text="No group has been created"
        var adapter:CustomAdapter
        recycleview.layoutManager = LinearLayoutManager(null,LinearLayoutManager.VERTICAL ,false)

        incomeViewModel.getallTag().observe(viewLifecycleOwner, Observer<List<Tag>> { it ->
            if (it.isNotEmpty()) {
               notification.visibility=View.INVISIBLE
               adapter = CustomAdapter(it)
               adapter.onItemClick = {
                   val action = IncomeFragmentDirections.actionNavigationIncomeToNavigationTransactionlist("Income",it)
                   Navigation.findNavController(root).navigate(action)
               }
               recycleview.adapter = adapter
           }else{

           }
        })

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}