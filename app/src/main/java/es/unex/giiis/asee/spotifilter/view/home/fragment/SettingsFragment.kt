package es.unex.giiis.asee.spotifilter.view.home.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import es.unex.giiis.asee.spotifilter.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}