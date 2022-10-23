package com.example.customproject.ui.account


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.AccountController
import com.example.customproject.model.Account


class AccountViewModel : ViewModel() {
    private val accountController:AccountController = AccountController()
    var account: MutableLiveData<List<Account>> = MutableLiveData()

    fun getInformation():LiveData<List<Account>>
    {
        var list: MutableList<Account> = mutableListOf()
        accountController.get().addOnSuccessListener { it->
            it.documents.forEach{it->
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