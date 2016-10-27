package br.com.klauskpm.thatsnewstome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference pageSize = findPreference(getString(R.string.pref_page_size_key));
            Preference orderBy = findPreference(getString(R.string.pref_order_by_key));
            Preference starRating = findPreference(getString(R.string.pref_star_rating_key));

            bindPreferenceSummaryToValue(pageSize, orderBy, starRating);
        }

        private void bindPreferenceSummaryToValue(Preference... preferences) {
            for (int i = 0; i < preferences.length; i++) {
                Preference preference = preferences[i];
                preference.setOnPreferenceChangeListener(this);
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext());
                String preferenceString = sharedPreferences.getString(preference.getKey(), "");
                onPreferenceChange(preference, preferenceString);
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] entries = listPreference.getEntries();
                    preference.setSummary(entries[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    }
}
