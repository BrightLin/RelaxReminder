package com.reminder.tasks;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import com.reminder.MyApp;
import com.reminder.actions.IAction;

/**
 * @author Bright. Create on 2017/3/24.
 */
public class TimerRemindTask extends AbstractTask {

    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean running = false;
    private boolean screenOn = true;
    private int repeatOfAction = 10;
    private long delayOfAction = 1000;
    private long delayOfTask = 1000 * 10;
    private RemindThread mThread;

    private TimerRemindTaskListener mListener;

    public TimerRemindTask(IAction action) {
        this(action, null);
    }

    public TimerRemindTask(IAction action, TimerRemindTaskListener listener) {
        actionType = action;
        mListener = listener;
        initTask();
    }

    private void initTask() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                running = true;
                mThread = new RemindThread();
                mThread.start();
            }
        };
    }

    @Override
    public void execute() {
        try {
            if (mTimerTask != null) {
                mTimerTask.cancel();
            }
            if (mTimer != null) {
                mTimer.cancel();
            }
            running = false;

            if (mThread != null) {
                mThread.join();
            }
            initTask();
            mTimer.schedule(mTimerTask, delayOfTask);

            if (mListener != null) {
                mListener.onTaskPrepared();
            }
        } catch (Exception e) {
            Log.i("Reminder", "Error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void cancel() {
        running = false;
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mListener != null) {
            mListener.onTaskCancel();
        }
    }

    private void delay() {
        try {
            Thread.sleep(delayOfAction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public TimerRemindTask setActionDelay(long delay) {
        this.delayOfAction = delay;
        return this;
    }

    public TimerRemindTask setTaskDelay(long delay) {
        this.delayOfTask = delay;
        return this;
    }

    public TimerRemindTask setActionRepeat(int repeat) {
        this.repeatOfAction = repeat;
        return this;
    }

    public TimerRemindTask setScreenOnEnable(boolean screenOn) {
        this.screenOn = screenOn;
        return this;
    }

    public void setTaskListener(TimerRemindTaskListener listener) {
        mListener = listener;
    }

    public interface TimerRemindTaskListener {
        void onTaskPrepared();

        void onTaskStart();

        void onTaskEnd();

        void onTaskCancel();
    }

    public class RemindThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (mListener != null) {
                mListener.onTaskStart();
            }

            if (screenOn) {
                PowerManager pm = (PowerManager) MyApp.getAppContext().getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock =
                        pm.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "TimerRemindTask");
                wakeLock.acquire();
                wakeLock.release();
            }

            int count = 0;
            while (running && count++ < repeatOfAction) {
                actionType.execute();
                delay();
            }

            if (mListener != null && count >= repeatOfAction) {
                mListener.onTaskEnd();
            }
        }
    }
}
