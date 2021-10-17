package org.rakulee.buup.model

data class BuupEmployerProfile(
    var userId : String = "",
    var firstName : String = "",
    var lastName : String = "",
    var email : String = "",
    var loginId : String = "",
    var password : String = "",      // hash
    var verified : Boolean = false,
    var photoUrl : String = "",
    var socialMedia : String = "",
    var timestamp : String = "",
    var companyInfo : CompanyInfo = CompanyInfo()
){
    data class CompanyInfo(
        var name : String = "",
        var logoUrl : String = "",
        var address1 : String = "",
        var address2 : String = "",
        var zipCode : String = "",
        var state : String = "",
        var description : String = "",
        var companySize : String = "",
        var budget : String = "",
        var industry : ArrayList<String> = ArrayList<String>()
    )
}
