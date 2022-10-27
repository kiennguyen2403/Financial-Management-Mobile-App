package com.example.customproject.ui.bottomfragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.R
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.example.customproject.ui.transaction_list.TransactionsListFragmant
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.api.Distribution.BucketOptions.Linear

class CustomBottomSheetDialogFragment(val transactionID: String,val transactionType: TransactionType,val tags:String):BottomSheetDialogFragment() {
    private lateinit var bottomFragmentViewModel: BottomFragmentViewModel
    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.bottom_fragment, container, false)
        bottomFragmentViewModel = ViewModelProvider(this)[BottomFragmentViewModel::class.java]
        val editButton = binding.findViewById<LinearLayout>(R.id.edit)
        val deleteButton= binding.findViewById<LinearLayout>(R.id.delete)

        editButton.setOnClickListener {
            updateTrans()
        }


        deleteButton.setOnClickListener {
            bottomFragmentViewModel.Delete(transactionType,tags,transactionID)
            val fragmentManager = activity?.supportFragmentManager
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().detach(this).commit()
                val navHost= fragmentManager.fragments[0]
                val frag =navHost.childFragmentManager.fragments[0]
                frag.getFragmentManager()?.beginTransaction()?.detach(frag)?.commit();
                frag.getFragmentManager()?.beginTransaction()?.attach(frag)?.commit();
            }

        }

        return binding.rootView
    }

    fun updateTrans(){
        val layoutParams = LinearLayout.LayoutParams(  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins( 60,20,60,20)

        val valueinput = EditText(context)
        valueinput.hint = "Value"
        valueinput.gravity = Gravity.CENTER
        valueinput.layoutParams = layoutParams
        valueinput.inputType = InputType.TYPE_CLASS_NUMBER


        val descinput = EditText(context)
        descinput.hint = "Description"
        descinput.gravity = Gravity.CENTER
        descinput.layoutParams = layoutParams



        val lp = LinearLayout(context)
        lp.orientation = LinearLayout.VERTICAL

        lp.addView( valueinput ,layoutParams)
        lp.addView( descinput,layoutParams )


        val builder = AlertDialog.Builder(context)

        builder.setMessage(" New transaction")
            .setPositiveButton("Create") { _, _ ->
                var data =  HashMap<String,Any>()
                data.put("desc",descinput.text)
                data.put("value",valueinput.text.toString().toInt())
                bottomFragmentViewModel.Update(transactionType, tags, data,transactionID)
                bottomFragmentViewModel.createNotification("You have earn " + valueinput.text.toString() + " for " + descinput.text.toString())
                val myToast = Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.START, 200, 200)
                myToast.show()
                val fragmentManager = activity?.supportFragmentManager
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().detach(this).commit()
                    val navHost= fragmentManager.fragments[0]
                    val frag =navHost.childFragmentManager.fragments[0]
                    frag.getFragmentManager()?.beginTransaction()?.detach(frag)?.commit();
                    frag.getFragmentManager()?.beginTransaction()?.attach(frag)?.commit();
                }


            }.setNegativeButton("Cancel") { _, _ ->

            }
            .setView(lp)
        val alertdialog = builder.create()
        alertdialog.show()
        if (valueinput.text.isEmpty() and descinput.text.isEmpty()) {
            alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }

        valueinput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (valueinput.text.isNotEmpty() and descinput.text.isNotEmpty())
                    alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
            }
        })

        descinput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (valueinput.text.isNotEmpty() and descinput.text.isNotEmpty()) {
                    alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }
            }

        })
    }

}