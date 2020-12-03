package com.example.mymedicalcompanion

class patientinfo {
    var useremail: String = " "
    var userpassword: String = " "
    var useruuid: String = " "
    var name: String = " "
    var nic: String = " "
    var dob:String=" "
    var gender:String=" "


    constructor(
        useremail: String,
        userpassword: String,
        useruuid: String,
        name: String,
        nic: String,
        dob: String,
        gender: String) {
        this.useremail = useremail
        this.userpassword = userpassword
        this.useruuid = useruuid
        this.name = name
        this.nic = nic
        this.dob = dob
        this.gender = gender

    }

}