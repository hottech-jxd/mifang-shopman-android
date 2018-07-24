package com.huotu.android.mifang

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.huotu.android.mifang", appContext.packageName)


        var json = "{\"a\":\"null\"}"

        var gson = GsonBuilder().serializeNulls().create()

        var s= gson.fromJson(json, t::class.java)

        json = "{\"a\":[\"ttt\"]}"

        var s2 = gson.fromJson(json,t::class.java)

    }

    class t(var a:ArrayList<String>?)
}
