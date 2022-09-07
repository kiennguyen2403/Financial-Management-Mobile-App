package com.example.customproject.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.databinding.FragmentNotificationsBinding
import com.example.customproject.model.Notification
import com.example.customproject.ui.notifications.CustomAdapter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.recyclerview
        var adapter: CustomAdapter
        recyclerView.layoutManager= LinearLayoutManager(null, LinearLayoutManager.VERTICAL ,false)
        notificationsViewModel.getallNotification().observe(viewLifecycleOwner,Observer<List<Notification>>(){
            if (it.size>0){
                adapter = CustomAdapter(it)
                recyclerView.adapter = adapter
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}