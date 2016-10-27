package br.com.klauskpm.thatsnewstome;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Created by Kazlauskas on 26/10/2016.
 */

public class NumberPickerPreference extends DialogPreference {

    private NumberPicker numberPicker;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateDialogView() {
        return generateNumberPicker();
    }

    public NumberPicker generateNumberPicker() {
        numberPicker = new NumberPicker(getContext());
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);

        /*
         * Anything else you want to add to this.
         */

        return numberPicker;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            int port = numberPicker.getValue();
            Log.d("NumberPickerPreference", "NumberPickerValue : " + port);
        }
    }

}