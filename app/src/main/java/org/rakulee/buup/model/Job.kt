package org.rakulee.buup.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.ktx.putOrIgnore

@ParseClassName("Job")
class Job : ParseObject(){
    var author: ParseUser?
        get() = getParseUser("Author")
        set(author) = putOrIgnore("Author", author)

    var ImageUrl : String
        get() = getString("ImageUrl").toString()
        set(ImageUrl) = put("ImageUrl", ImageUrl)

    var companyTitle : String
        get() = getString("Title").toString()
        set(companyTitle) = put("Title", companyTitle)

    var jobDescription : String
        get() = getString("Description").toString()
        set(jobDescription) = put("Description", jobDescription)

    var jobLocation : String
        get() = getString("Location").toString()
        set(jobLocation) = put("Location", jobLocation)

    var jobRequirement : String
        get() = getString("Requirements").toString()
        set(jobRequirement) = put("Requirements", jobRequirement)

    var jobPay : String
        get() = getString("Pay").toString()
        set(jobPay) = put("Pay", jobPay)
}