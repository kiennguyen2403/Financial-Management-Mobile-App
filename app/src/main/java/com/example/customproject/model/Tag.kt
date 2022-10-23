package com.example.customproject.model

import com.google.type.Color

class Tag(_name:String,_color:String, _type: TransactionType) {

    var name:String=_name
        set(value){
        name = value
    }

    var color: String=_color
        set(value) {
        color = value
    }

    var type:TransactionType=_type
        set(value) {
        type = value
    }


}