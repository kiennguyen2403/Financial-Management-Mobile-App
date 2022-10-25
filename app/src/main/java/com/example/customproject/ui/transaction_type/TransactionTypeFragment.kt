package com.example.customproject.ui.transaction_type

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.customproject.R

class TransactionTypeFragment : Fragment() {

    private lateinit var viewModel: TransactionTypeViewModel
    private lateinit var collectionPagerAdapter: CollectionPagerAdapter
    private lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction_type, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionPagerAdapter = CollectionPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = collectionPagerAdapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TransactionTypeViewModel::class.java]
        // TODO: Use the ViewModel
    }

}

