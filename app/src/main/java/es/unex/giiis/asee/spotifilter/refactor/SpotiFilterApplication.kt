package es.unex.giiis.asee.spotifilter.refactor

import android.app.Application

class SpotiFilterApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }

}