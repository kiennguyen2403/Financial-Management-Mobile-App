package com.example.customproject.ui.transaction_type

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.customproject.ui.transaction.TransactionFragment

private const val ARG_OBJECT = "object"

class CollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(i: Int): Fragment {
        val fragment = TransactionFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, i + 1)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (position == 0) {
            "Income"
        } else {
            "Spending"
        }
    }
}