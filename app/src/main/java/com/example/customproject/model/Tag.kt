package com.example.customproject.model

import com.google.type.Color

class Tag(_name:String,_color:String, _type: TransactionType,_id:String?) {

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
    var id:String?=_id
        set(value) {
            this.id=value
        }

}