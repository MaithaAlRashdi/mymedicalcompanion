package com.example.mymedicalcompanion

class doctorinfo {
    var useremail:String=" "
    var userpassword:String=" "
    var userid:String=" "
    var userContact:String=" "
    var userspecializaton:String=" "
    var name:String=" "
    var dob:String=" "
    var gender:String=" "
    var nicCard:String=" "


    constructor(
        useremail: String,
        userpassword: String,
        userid: String,
        userContact: String,
        userspecializaton: String,
        name: String,
        dob: String,
        gender: String,
        nicCard: String
    ) {
        this.useremail = useremail
        this.userpassword = userpassword
        this.userid = userid
        this.userContact = userContact
        this.userspecializaton = userspecializaton
        this.name = name
        this.dob = dob
        this.gender = gender
        this.nicCard = nicCard
    }
}