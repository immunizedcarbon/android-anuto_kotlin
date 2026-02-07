package ch.logixisland.anuto

import android.app.Application

class AnutoApplication : Application() {

    lateinit var gameFactory: GameFactory
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        gameFactory = GameFactory(applicationContext)
    }

    companion object {
        @Volatile
        private lateinit var instance: AnutoApplication

        @JvmStatic
        fun getInstance(): AnutoApplication = instance
    }

    fun getGameFactory(): GameFactory = gameFactory
}
