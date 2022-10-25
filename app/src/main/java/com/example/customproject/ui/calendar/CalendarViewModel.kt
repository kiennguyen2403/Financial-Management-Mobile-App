package com.example.customproject.ui.calendar

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.TransactionType
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.Timestamp

class CalendarViewModel : ViewModel(){
    private val eventlist: MutableLiveData<List<Event>> = MutableLiveData()
    private val transactionController = TransactionController()

    fun getEvent():LiveData<List<Event>>{
        val res: MutableList<Event> = mutableListOf()
        var list :MutableList<String>
        transactionController.getAll(TransactionType.Income).addOnSuccessListener { it ->
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { it ->
                transactionController.getSpecific(TransactionType.Income,it).addOnSuccessListener{ it->
                    it.documents.forEach { it->
                        it.reference.addSnapshotListener{ value, _ ->
                            if (value != null)
                            {
                                if (value.data?.get("value") != null)
                                {
                                    val timestamp:Timestamp= value.data?.get("date") as Timestamp
                                     val event = Event(Color.GREEN, timestamp.seconds*1000,value.data?.get("desc"))
                                      res.add(event)
                                      eventlist.value=res
                                }
                            }
                        }
                    }
                }
            }
        }
        transactionController.getAll(TransactionType.Spending).addOnSuccessListener { it ->
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { it ->
                transactionController.getSpecific(TransactionType.Spending,it).addOnSuccessListener{ it->
                    it.documents.forEach { it->
                        it.reference.addSnapshotListener{ value, _ ->
                            if (value != null)
                            {
                                if (value.data?.get("value") != null)
                                {
                                    val timestamp:Timestamp= value.data?.get("date") as Timestamp
                                    val event = Event(Color.RED, timestamp.seconds*1000,value.data?.get("desc"))
                                    res.add(event)
                                    eventlist.value=res
                                }

                            }
                        }
                    }
                }
            }
        }
        return eventlist
    }
}