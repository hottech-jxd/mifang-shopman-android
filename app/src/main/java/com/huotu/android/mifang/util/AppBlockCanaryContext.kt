package com.huotu.android.mifang.util

import com.github.moduth.blockcanary.BlockCanaryContext
import java.io.File

class AppBlockCanaryContext : BlockCanaryContext() {

    override fun provideQualifier(): String {
        return super.provideQualifier()
    }

    override fun provideUid(): String {
        return super.provideUid()
    }

    override fun provideBlockThreshold(): Int {
        return super.provideBlockThreshold()
    }

    override fun provideDumpInterval(): Int {
        return super.provideDumpInterval()
    }

    override fun providePath(): String {
        return super.providePath()
    }

    override fun displayNotification(): Boolean {
        return super.displayNotification()
    }

    override fun zip(src: Array<out File>?, dest: File?): Boolean {
        return super.zip(src, dest)
    }
}