package ch.logixisland.anuto.view.game

import android.content.Context
import androidx.preference.PreferenceManager
import ch.logixisland.anuto.Preferences

class BackButtonControl(context: Context) {

    enum class BackButtonAction {
        DO_NOTHING,
        SHOW_TOAST,
        EXIT
    }

    private enum class BackButtonMode {
        DISABLED,
        ENABLED,
        TWICE
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    private var lastBackButtonPress = 0L

    fun backButtonPressed(): BackButtonAction {
        val timeNow = System.currentTimeMillis()

        return when (getBackButtonMode()) {
            BackButtonMode.ENABLED -> BackButtonAction.EXIT
            BackButtonMode.TWICE -> {
                if (timeNow < lastBackButtonPress + BACK_TWICE_INTERVAL) {
                    BackButtonAction.EXIT
                } else {
                    lastBackButtonPress = timeNow
                    BackButtonAction.SHOW_TOAST
                }
            }
            BackButtonMode.DISABLED -> BackButtonAction.DO_NOTHING
        }
    }

    private fun getBackButtonMode(): BackButtonMode {
        val backModeString = preferences.getString(Preferences.BACK_BUTTON_MODE, null)
        return runCatching { BackButtonMode.valueOf(backModeString.orEmpty()) }
            .getOrElse { BackButtonMode.DISABLED }
    }

    companion object {
        private const val BACK_TWICE_INTERVAL = 2000L
    }
}
