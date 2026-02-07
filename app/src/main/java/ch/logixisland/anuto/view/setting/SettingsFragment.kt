package ch.logixisland.anuto.view.setting

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ch.logixisland.anuto.AnutoApplication
import ch.logixisland.anuto.Preferences
import ch.logixisland.anuto.R

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val gameFactory = AnutoApplication.getInstance().gameFactory
    private val gameLoader = gameFactory.gameLoader
    private val gameState = gameFactory.gameState
    private val highScores = gameFactory.highScores
    private val tutorialControl = gameFactory.tutorialControl
    private val listPreferenceKeys = mutableSetOf<String>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        registerListPreference(Preferences.BACK_BUTTON_MODE)
        registerListPreference(Preferences.THEME_INDEX)
        setupChangeThemeConfirmationDialog()
        setupResetHighscores()
        setupResetTutorial()
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (listPreferenceKeys.contains(key)) {
            updateListPreferenceSummary(key)
        }

        if (Preferences.THEME_INDEX == key) {
            gameLoader.restart()
        }
    }

    private fun registerListPreference(key: String) {
        listPreferenceKeys.add(key)
        updateListPreferenceSummary(key)
    }

    private fun updateListPreferenceSummary(key: String) {
        val preference = findPreference<ListPreference>(key) ?: return
        preference.summary = preference.entry
    }

    private fun setupChangeThemeConfirmationDialog() {
        val themePreference = findPreference<ListPreference>(Preferences.THEME_INDEX) ?: return
        themePreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                if (!gameState.isGameStarted) {
                    return@OnPreferenceChangeListener true
                }

                AlertDialog.Builder(preference.context)
                    .setTitle(R.string.change_theme)
                    .setMessage(R.string.change_theme_warning)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        themePreference.value = newValue.toString()
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.alert)
                    .show()
                false
            }
    }

    private fun setupResetHighscores() {
        val preference = findPreference<Preference>(PREF_RESET_HIGHSCORES) ?: return
        preference.setOnPreferenceClickListener { clickedPreference ->
            AlertDialog.Builder(clickedPreference.context)
                .setTitle(R.string.reset_highscores)
                .setMessage(R.string.reset_highscores_warning)
                .setPositiveButton(android.R.string.yes) { _, _ -> highScores.clearHighScores() }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.alert)
                .show()
            true
        }
    }

    private fun setupResetTutorial() {
        val preference = findPreference<Preference>(PREF_START_TUTORIAL) ?: return
        preference.setOnPreferenceClickListener { clickedPreference ->
            AlertDialog.Builder(clickedPreference.context)
                .setTitle(R.string.start_tutorial)
                .setMessage(R.string.start_tutorial_warning)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    tutorialControl.restart()
                    gameLoader.restart()
                    requireActivity().finish()
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.alert)
                .show()
            true
        }
    }

    companion object {
        private const val PREF_RESET_HIGHSCORES = "reset_highscores"
        private const val PREF_START_TUTORIAL = "start_tutorial"
    }
}
