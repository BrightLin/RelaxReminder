package com.reminder.actions;

import android.content.Context;
import android.os.Vibrator;

import com.reminder.MyApp;

/**
 * @author Bright. Create on 2017/3/24.
 */
public class VibrateAction implements IAction {
    private long milliseconds = 600;
    private Vibrator vibrator;

    public VibrateAction() {
        this(600);
    }

    public VibrateAction(long ms) {
        vibrator = (Vibrator) MyApp.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        milliseconds = ms;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public void execute() {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

    @Override
    public void cancel() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
