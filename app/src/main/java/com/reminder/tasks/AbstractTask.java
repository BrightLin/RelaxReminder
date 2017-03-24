package com.reminder.tasks;

import com.reminder.actions.IAction;

/**
 * @author Bright. Create on 2017/3/24.
 */
public abstract class AbstractTask {

    protected IAction actionType;

    public IAction getActionType() {
        return actionType;
    }

    public void setActionType(IAction actionType) {
        this.actionType = actionType;
    }

    public abstract void execute();
    public abstract void cancel();
}
