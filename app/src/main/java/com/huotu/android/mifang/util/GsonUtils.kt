package com.huotu.android.mifang.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Administrator on 2017/10/10.
 */

class GsonUtils<T> {

    fun toJson(t: T): String {
        return gson!!.toJson(t)
    }

//    fun toBean(msg: String, t: T): T {
//        // 这里起初使用
//        // Type type = TypeToken<T>() {}.getType());
//        // return (T) gson.fromJson(msg,type);
//        // 貌似java泛型不具有传递性。只能采用Class参数的方法。有大神可以解决Type的问题请留言告知。THX。
//        return gson!!.fromJson<out Any>(msg, t.javaClass) as T
//    }


    fun type(raw: Class<*>, vararg args: Type): ParameterizedType {
        return object : ParameterizedType {
            override fun getRawType(): Type {
                return raw
            }

            override fun getActualTypeArguments(): Array<Type> {
                return args as Array<Type>
            }

            override fun getOwnerType(): Type? {
                return null
            }
        }
    }


    companion object {
        var gson: Gson? = null
            private set

        var gsonUtils:GsonUtils<Any>?=null

        init {
            if (null == gson) {
                gson = GsonBuilder().serializeNulls().create()
            }
            if(null== gsonUtils){
                gsonUtils = GsonUtils()
            }
        }


    }

}


// class NullStringToEmptyAdapterFactory<T> : TypeAdapterFactory {
//
//
//     override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
//         var rawType =  type!!.getRawType() as Class<T>
//         if (rawType != String::class.java) {
//                     return null
//                 }
//             return StringNullAdapter() as (TypeAdapter<T>)
//     }
// }


//class StringNullAdapter : TypeAdapter<String>() {
//
//    override fun write(out: JsonWriter?, value: String?) {
//        if (value == null) {
//            out!!.nullValue()
//            return
//        }
//        out!!.value(value)
//    }
//
//    override fun read( reader : JsonReader?): String {
//        if ( reader!!.peek() == JsonToken.NULL) {
//            reader.nextNull()
//            return ""
//        }
//        return reader.nextString()
//    }
//
//}
