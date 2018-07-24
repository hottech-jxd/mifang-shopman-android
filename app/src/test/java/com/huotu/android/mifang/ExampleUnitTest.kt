package com.huotu.android.mifang

import com.google.gson.GsonBuilder
import com.huotu.android.mifang.util.NullStringToEmptyAdapterFactory
import org.junit.Test

import org.junit.Assert.*

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
    class ttt(var a:ArrayList<String>?)




}
