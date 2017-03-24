package com.reminder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.reminder.actions.RingAndVibrateAction;
import com.reminder.tasks.TimerRemindTask;

public class MainActivity extends AppCompatActivity {

    TimerRemindTask task = new TimerRemindTask(new RingAndVibrateAction(500));

    MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            task.setTaskListener(new TimerRemindTask.TimerRemindTaskListener() {
                @Override
                public void onTaskPrepared() {
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onTaskStart() {
                    handler.sendEmptyMessage(1);
                }

                @Override
                public void onTaskEnd() {
                    handler.sendEmptyMessage(2);
                }

                @Override
                public void onTaskCancel() {
                    handler.sendEmptyMessage(3);
                }
            });
            task.execute();
        } else if (view.getId() == R.id.button2) {
            task.cancel();
        }

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this, "onTaskPrepared", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "onTaskStart", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "onTaskEnd", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(MainActivity.this, "onTaskCancel", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
