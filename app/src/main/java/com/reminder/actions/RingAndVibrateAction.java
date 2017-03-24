package com.reminder.actions;

/**
 * @author Bright. Create on 2017/3/24.
 */
public class RingAndVibrateAction implements IAction {
    private RingAction mRingAction;
    private VibrateAction mVibrateAction;

    public RingAndVibrateAction() {
        this(600);
    }

    public RingAndVibrateAction(long ms) {
        mRingAction = new RingAction();
        mVibrateAction = new VibrateAction(ms);
    }

    @Override
    public void execute() {
        if (mRingAction != null) {
            mRingAction.execute();
        }
        if (mVibrateAction != null) {
            mVibrateAction.execute();
        }
    }

    @Override
    public void cancel() {
        if (mRingAction != null) {
            mRingAction.cancel();
        }
        if (mVibrateAction != null) {
            mVibrateAction.cancel();
        }
    }

    public void setMilliseconds(long milliseconds) {
        mVibrateAction.setMilliseconds(milliseconds);
    }
}
