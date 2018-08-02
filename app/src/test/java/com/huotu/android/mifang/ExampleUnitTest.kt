package com.huotu.android.mifang

import com.google.gson.GsonBuilder
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //assertEquals(4, 2 + 2)

        var json = "{\"a\":[\"ttt\"]}"


        var gson = GsonBuilder().serializeNulls().create()

        var s= gson.fromJson(json, ttt::class.java)

        json = "{\"a\":null}"


        var s2 = gson.fromJson(json, ttt::class.java)



    }


    @Test
    fun cltest(){
        var t ="a,b"
        var iamges :ArrayList<String>

        var iamges1 = t.split(",")

        iamges = ArrayList(iamges1)

        t ="c"
        var iamges2 = t.split(",")

        iamges = ArrayList(iamges2)





    }

    class ttt(var a:ArrayList<String>?)




}
