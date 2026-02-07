package ch.logixisland.anuto.view.game

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import ch.logixisland.anuto.AnutoApplication
import ch.logixisland.anuto.R
import ch.logixisland.anuto.engine.theme.ActivityType
import ch.logixisland.anuto.view.AnutoActivity
import ch.logixisland.anuto.view.ApplySafeInsetsHandler

class GameActivity : AnutoActivity() {

    private val gameFactory by lazy { AnutoApplication.getInstance().gameFactory }
    private val gameLoader = gameFactory.gameLoader
    private val gameSaver = gameFactory.gameSaver
    private val gameEngine = gameFactory.gameEngine
    private val towerSelector = gameFactory.towerSelector
    private val backButtonControl = BackButtonControl(AnutoApplication.getInstance())

    private var backButtonToast: Toast? = null

    private lateinit var towerDefenseView: GameView

    override val activityType: ActivityType = ActivityType.Game

    override fun onCreate(savedInstanceState: Bundle?) {
        gameLoader.autoLoadGame()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        findViewById(android.R.id.content).setOnApplyWindowInsetsListener(ApplySafeInsetsHandler())

        val config = resources.configuration
        if ((config.screenLayout and Configuration.SCREENLAYOUT_LONG_MASK) == Configuration.SCREENLAYOUT_LONG_YES) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        towerDefenseView = findViewById(R.id.view_tower_defense)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (towerSelector.isTowerSelected) {
                    towerSelector.selectTower(null)
                    return
                }

                when (backButtonControl.backButtonPressed()) {
                    BackButtonControl.BackButtonAction.DO_NOTHING -> Unit
                    BackButtonControl.BackButtonAction.SHOW_TOAST -> {
                        backButtonToast = showBackButtonToast()
                    }
                    BackButtonControl.BackButtonAction.EXIT -> {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                        isEnabled = true
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        gameEngine.start()
    }

    override fun onPause() {
        super.onPause()
        gameSaver.autoSaveGame()
        gameEngine.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        towerDefenseView.close()
        backButtonToast?.cancel()
    }

    private fun showBackButtonToast(): Toast {
        val message = getString(R.string.press_back_button_again_toast)
        return Toast.makeText(this, message, Toast.LENGTH_SHORT).apply { show() }
    }
}
