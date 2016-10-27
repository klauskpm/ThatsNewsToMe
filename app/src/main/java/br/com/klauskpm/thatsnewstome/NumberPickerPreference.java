package br.com.klauskpm.thatsnewstome;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

/**
 * Created by Kazlauskas on 26/10/2016.
 */

public class NumberPickerPreference extends DialogPreference {
    private final static String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    private final static int INT_NOT_DEFINED = -999;

    private NumberPicker numberPicker;
    private boolean mMaxValueWasSet = false;
    private boolean mMinValueWasSet = false;

    private int mMaxValue;
    private int mMinValue;

    private int mDefaultValue;

    private String mPrefKey;
    private int mPrefValue;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        int prefKeyResourceId = attrs.getAttributeResourceValue(ANDROID_NS, "key", 0);
        mPrefKey = context.getString(prefKeyResourceId);

        int maxValue = attrs.getAttributeIntValue(null, "maxValue", 0);
        setMaxValue(maxValue);

        int minValue = attrs.getAttributeIntValue(null, "minValue", 0);
        setMinValue(minValue);

        int defaultResourceId = attrs.getAttributeResourceValue(ANDROID_NS, "defaultValue", 0);
        int defaultValue = Integer.parseInt(context.getString(defaultResourceId));

        if (defaultValue < minValue || defaultValue == 0)
            defaultValue = minValue;
        mDefaultValue = defaultValue;

        Log.d("NumberPickerPreference", "NumberPickerPreference: " + mDefaultValue);

        int prefValue = getSharedPreferences().getInt(mPrefKey, INT_NOT_DEFINED);
        if (prefValue == INT_NOT_DEFINED)
            prefValue = mDefaultValue;

        mPrefValue = prefValue;
    }

    @Override
    protected View onCreateDialogView() {
        return generateNumberPicker();
    }

    public NumberPicker generateNumberPicker() {
        numberPicker = new NumberPicker(getContext());
        if (mMinValueWasSet)
            numberPicker.setMinValue(mMinValue);

        if (mMaxValueWasSet)
            numberPicker.setMaxValue(mMaxValue);

        numberPicker.setValue(mPrefValue);

        /*
         * Anything else you want to add to this.
         */

        return numberPicker;
    }

    public void setMaxValue(int mMaxValue) {
        this.mMaxValue = mMaxValue;
        this.mMaxValueWasSet = true;
    }

    public void setMinValue(int mMinValue) {
        this.mMinValue = mMinValue;
        this.mMinValueWasSet = true;
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        setSummary("" + mPrefValue);
        return super.onCreateView(parent);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            mPrefValue = numberPicker.getValue();
            setSummary("" + mPrefValue);
            Log.d("NumberPickerPreference", "NumberPickerValue : " + mPrefValue);
        }
    }

}