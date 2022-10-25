package com.example.customproject.ui.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.recyclerview
        val notification= binding.notification
        val progressBar:ProgressBar = binding.progressBar4
        notification.text="No transaction has been recorded"
        var adapter: CustomAdapter
        recyclerView.layoutManager= LinearLayoutManager(null, LinearLayoutManager.VERTICAL ,false)
        notificationsViewModel.getallNotification(progressBar).observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                notification.visibility = View.INVISIBLE
                adapter = CustomAdapter(it)
                recyclerView.adapter = adapter
            }
        }
        return root
    }
}