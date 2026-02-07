package ch.logixisland.anuto.view

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import ch.logixisland.anuto.AnutoApplication
import ch.logixisland.anuto.GameFactory
import ch.logixisland.anuto.engine.theme.ActivityType
import ch.logixisland.anuto.engine.theme.Theme
import ch.logixisland.anuto.engine.theme.ThemeManager

abstract class AnutoActivity : FragmentActivity(), ThemeManager.Listener {

    protected val gameFactory: GameFactory by lazy {
        AnutoApplication.getInstance().gameFactory
    }

    private val themeManager: ThemeManager by lazy {
        gameFactory.themeManager
    }

    protected abstract val activityType: ActivityType

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themeManager.theme.getActivityThemeId(activityType))
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        themeManager.addListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        themeManager.removeListener(this)
    }

    override fun themeChanged(theme: Theme) {
        recreate()
    }
}
