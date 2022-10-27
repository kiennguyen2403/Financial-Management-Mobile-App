package com.example.customproject.model

import com.google.firebase.Timestamp


class Transaction( _value:Number, _desc:String,_date:Timestamp,_id:String?){
    var value:Number=_value
        set(value) {
        this.value=value
    }

    var desc:String= _desc
        set(value) {
        this.desc=value
    }

    var date:Timestamp= _date
        set(value) {
            this.date=value
        }
    var id:String?=_id
        set(value) {
            this.id=value
        }

}