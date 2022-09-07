package com.example.customproject.model

import com.google.firebase.Timestamp


class Transaction( _value:Number, _desc:String,_date:Timestamp){



    var value:Number=_value
    get()=field
    set(value) {
        this.value=value
    }

    var desc:String= _desc
    get() = field
    set(value) {
        this.desc=value
    }

    var date:Timestamp= _date
        get() = field
        set(value) {
            this.date=value
        }

}