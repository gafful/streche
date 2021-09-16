package com.gafful.streche

class Greeting {
    fun greeting(): String {
        //return "Hello, ${Platform().platform}!"
        return "Guess what it is! > ${Platform().platform.reversed()}!"
    }
}