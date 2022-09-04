package com.example.customproject.model

class Transaction(_type: TransactionType, _value:Number, _desc:String, _tag:Tag){

    var type:TransactionType=_type
    get()= field
    set(value){
        this.type=value
    }

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

    var tag:Tag = _tag
    get() = field
    set(value){
        this.tag=value
    }
}