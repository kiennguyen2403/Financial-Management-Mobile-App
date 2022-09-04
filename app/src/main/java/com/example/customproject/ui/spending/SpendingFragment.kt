package com.example.customproject.ui.spending

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.controller.TransactionController
import com.example.customproject.databinding.FragmentSpendingBinding
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.example.customproject.ui.income.CustomAdapter
import com.example.customproject.ui.spending.SpendingViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SpendingFragment : Fragment() {

    private var _binding: FragmentSpendingBinding? = null
    private var transactionController: TransactionController = TransactionController()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val spendingViewModel =
            ViewModelProvider(this).get(SpendingViewModel::class.java)

        _binding = FragmentSpendingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recycleview: RecyclerView = binding.recyclerview
        var adapter: CustomAdapter
        recycleview.layoutManager = LinearLayoutManager(null, LinearLayoutManager.VERTICAL ,false)

        spendingViewModel.GetallData().observe(viewLifecycleOwner, Observer<List<Transaction>>() {
            if (it.size>0) {
                adapter = CustomAdapter(it)
                recycleview.adapter = adapter
            }
        })

        val button: FloatingActionButton =binding.fab
        button.setOnClickListener{
            val layoutParams = LinearLayout.LayoutParams(  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins( 60,20,60,20);

            val valueinput = EditText(activity)
            valueinput.setHint("Value")
            valueinput.setGravity( Gravity.CENTER)
            valueinput.setLayoutParams(layoutParams);
            valueinput.inputType = InputType.TYPE_CLASS_NUMBER


            val descinput = EditText(activity)
            descinput.setHint("Description")
            descinput.setGravity( Gravity.CENTER)
            descinput.setLayoutParams(layoutParams);

            val lp = LinearLayout(activity)
            lp.setOrientation( LinearLayout.VERTICAL )

            lp.addView( valueinput ,layoutParams);
            lp.addView( descinput,layoutParams );



            val builder = AlertDialog.Builder(activity)


            builder.setMessage(" New Spending")
                .setPositiveButton("Create", DialogInterface.OnClickListener { dialog, id ->
                        val tag = Tag("Food")
                        var result = transactionController.Create(
                            TransactionType.Spending,
                            valueinput.text.toString().toInt(),
                            descinput.text.toString(),
                            tag
                        )
                        transactionController.Add(result)
                        val myToast =
                            Toast.makeText(activity, "Add Successfully", Toast.LENGTH_SHORT)
                        myToast.setGravity(Gravity.LEFT, 200, 200)
                        myToast.show()

                })
                .setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog,id ->


                })
                .setView(lp)
            val alertdialog = builder.create()
            alertdialog.show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}