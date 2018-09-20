package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.helper.PrefHelper;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import static com.usermanual.helper.Consts.*;

public class SettingsFragment extends Fragment implements OnSeekChangeListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "SettingsFragment";

    IndicatorSeekBar seekBar;
    TextView sampleTextView;
    RadioGroup animationRadioGroup;
    RadioButton animOn, animOff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        seekBar = (IndicatorSeekBar) view.findViewById(R.id.seek_bar);
        sampleTextView = (TextView) view.findViewById(R.id.font_sample_text_view);
        animationRadioGroup = (RadioGroup) view.findViewById(R.id.animation_radio_group);
        animOn = (RadioButton) view.findViewById(R.id.anim_on);
        animOff = (RadioButton) view.findViewById(R.id.anim_off);
        if (PrefHelper.getBoolean(getContext(), PREF_ANIMATIONS, true)) {
            animOn.setChecked(true);
            animOff.setChecked(false);
        } else {
            animOn.setChecked(false);
            animOff.setChecked(true);
        }
        seekBar.setOnSeekChangeListener(this);
        animationRadioGroup.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onSeeking(SeekParams seekParams) {
        Log.e(TAG, "onSeeking: " + seekParams.progress);
        sampleTextView.setTextSize(seekParams.progress);
        PrefHelper.saveInt(getContext(), PREF_FONT_SIZE, seekParams.progress);
    }

    @Override
    public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.anim_on)
            PrefHelper.saveBoolean(getContext(), PREF_ANIMATIONS, true);
        if (checkedId == R.id.anim_off)
            PrefHelper.saveBoolean(getContext(), PREF_ANIMATIONS, false);
    }
}
