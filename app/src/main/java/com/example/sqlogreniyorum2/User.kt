package com.example.sqlogreniyorum2

class User {

    var id : Int = 0
    var adsoyad:String=""
    var yas:Int=0
    var memleket:String=""
    var tel:String=""

    constructor(adsoyad:String,yas:Int,memleket:String,tel:String){
        this.adsoyad=adsoyad
        this.yas=yas
        this.memleket=memleket
        this.tel=tel
    }
    constructor(){

    }
}