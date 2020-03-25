package com.tbright.ktbaseproject.demo

import com.tbright.ktbaseproject.demo.bean.Token

object GlobalConstants {

    var token : Token? = null

    val authorization: String?
        get() = if (token != null) token!!.token_type + " " + token!!.access_token
        else null
}