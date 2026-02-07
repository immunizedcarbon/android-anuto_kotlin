package ch.logixisland.anuto.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ch.logixisland.anuto.Preferences

open class AnutoFragment : Fragment() {

    protected fun updateMenuTransparency() {
        val view: View = view ?: return
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val transparentMenusEnabled = preferences.getBoolean(Preferences.TRANSPARENT_MENUS_ENABLED, false)

        view.alpha = if (transparentMenusEnabled) 0.73f else 1.0f
    }
}
