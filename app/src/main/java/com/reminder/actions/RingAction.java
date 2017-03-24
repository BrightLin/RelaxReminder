package com.reminder.actions;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.reminder.MyApp;

/**
 * @author Bright. Create on 2017/3/24.
 */
public class RingAction implements IAction {

    private Ringtone mRing;

    public RingAction() {
        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mRing = RingtoneManager.getRingtone(MyApp.getAppContext(), defaultUri);
    }

    @Override
    public void execute() {
        if (mRing != null) {
            mRing.play();
        }
    }

    @Override
    public void cancel() {
        if (mRing != null && mRing.isPlaying()) {
            mRing.stop();
        }
    }
}
