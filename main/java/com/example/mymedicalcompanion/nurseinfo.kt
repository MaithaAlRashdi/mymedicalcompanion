package com.example.mymedicalcompanion

class nurseinfo {
    var useremail: String = " "
    var userpassword: String = " "
    var userid: String = " "
    var name: String = " "
    var dob:String=" "
    var gender:String=" "
    var nicCard:String=" "

    constructor(useremail: String, userpassword: String, userid: String, name: String ,dob: String,
                gender: String,
                nicCard: String
    ) {
        this.useremail = useremail
        this.userpassword = userpassword
        this.userid = userid
        this.name = name
        this.dob = dob
        this.gender = gender
        this.nicCard = nicCard
    }
}