package com.example.customproject.model

import com.google.firebase.Timestamp

class Notification(_desc:String) {

    var desc:String= _desc
        set(value) {
            this.desc=value
        }
    var date:Timestamp = Timestamp.now()
}