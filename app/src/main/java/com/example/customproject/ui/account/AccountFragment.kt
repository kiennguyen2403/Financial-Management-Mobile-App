package com.example.customproject.ui.account

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.customproject.MainActivity
import com.example.customproject.R
import com.example.customproject.databinding.FragmentAccountBinding
import com.example.customproject.model.Account
import com.example.customproject.ui.login.LoginActivity

class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel
    private val binding get() = _binding!!
    private var _binding: FragmentAccountBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var name:TextView = binding.Name
        var accountNumber:TextView = binding.Account
        var bsbNumber:TextView = binding.bsb
        var email:TextView = binding.email
        var dob:TextView = binding.dob
        viewModel.getInformation().observe(viewLifecycleOwner,Observer<List<Account>>{
            name.text= it[0].name
            accountNumber.text = "Account number     "+it[0].accountnumber
            bsbNumber.text =     "BSB                            "+it[0].BSB
            email.text =         "Email              "+it[0].email
            dob.text   =         "DOB                "+it[0].dob

        })
        val imageButton: ImageButton = binding.imageButton
        imageButton.setOnClickListener {
           val intent:Intent = Intent(this.context,LoginActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel

    }

}