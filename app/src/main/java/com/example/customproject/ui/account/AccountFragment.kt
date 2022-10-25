package com.example.customproject.ui.account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.databinding.FragmentAccountBinding
import com.example.customproject.ui.login.LoginActivity

class AccountFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel
    private val binding get() = _binding!!
    private var _binding: FragmentAccountBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        ViewModelProvider(this)[AccountViewModel::class.java].also { viewModel = it }
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val name:TextView = binding.Name
        val accountNumber:TextView = binding.Account
        val bsbNumber:TextView = binding.bsb
        val email:TextView = binding.email
        val dob:TextView = binding.dob
        val progressBar = binding.progressBar5

        viewModel.getInformation(progressBar).observe(viewLifecycleOwner) {
            name.text = it[0].name
            accountNumber.text = "Account number:" + it[0].accountnumber
            bsbNumber.text = "BSB:" + it[0].BSB
            email.text = "Email:" + it[0].email
            dob.text = "DOB:" + it[0].dob
        }
        val imageButton: ImageButton = binding.imageButton
        imageButton.setOnClickListener {
           val intent = Intent(this.context,LoginActivity::class.java)
            startActivity(intent)
        }
        return root
    }

}