package com.example.customproject.ui.account


import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.AccountController
import com.example.customproject.model.Account


class AccountViewModel : ViewModel() {
    private val accountController:AccountController = AccountController()
    private var account: MutableLiveData<List<Account>> = MutableLiveData()

    fun getInformation(loading:ProgressBar):LiveData<List<Account>>
    {
        val list: MutableList<Account> = mutableListOf()
        accountController.get().addOnSuccessListener { it->
            loading.visibility = View.GONE
            it.documents.forEach{
                val name = it.data?.get("name").toString()
                val BSB = it.data?.get("BSB").toString()
                val accountnumber = it.data?.get("Account").toString()
                val email = it.data?.get("email").toString()
                val dob = it.data?.get("DOB").toString()
                val res = Account(name, BSB, accountnumber,email,dob)
                list.add(res)
                account.value=list
            }
        }
        return account
    }
}