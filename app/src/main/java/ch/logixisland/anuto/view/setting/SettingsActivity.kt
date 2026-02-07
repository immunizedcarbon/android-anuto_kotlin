package ch.logixisland.anuto.view.setting

import android.os.Bundle
import ch.logixisland.anuto.engine.theme.ActivityType
import ch.logixisland.anuto.view.AnutoActivity
import ch.logixisland.anuto.view.ApplySafeInsetsHandler

class SettingsActivity : AnutoActivity() {
    override val activityType: ActivityType = ActivityType.Normal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()

        findViewById(android.R.id.content).setOnApplyWindowInsetsListener(ApplySafeInsetsHandler())
    }
}
