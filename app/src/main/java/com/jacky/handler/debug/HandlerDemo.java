package com.jacky.handler.debug;


import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

class HandlerDemo {
    private final Handler handler;
    private static final String tag = "HandlerDemo";

    int token = -1;
    final CountDownLatch countDownLatch = new CountDownLatch(1);

    HandlerDemo() {
        final HandlerThread handlerThread = new HandlerThread("jacky-io-thread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.obj instanceof Integer) {
                    final int value = (int) msg.obj;
                    Log.d(tag, "handle=>" + value);
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    void start() {
        delayToAddBarrier();
        delayToRemoveBarrier();

        for (int i = 0; i < 20; i++) {
            boolean isAsynchronous = false;

            final Message obtain = Message.obtain();
            obtain.obj = i;
            if (i == 5) {
                isAsynchronous = true;
                obtain.setAsynchronous(true);
            }

            handler.sendMessageDelayed(obtain, !isAsynchronous ? (i + 1) * 200 : 10 * 1000);
        }
    }

    private void delayToAddBarrier() {
        new Thread("add-barrier") {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                    final int token = invokeSyncBarrier();
                    Log.d(tag, "invoke sync barrier " + (token >= 0 ? "success" : "fail"));
                    HandlerDemo.this.token = token;
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void delayToRemoveBarrier() {
        new Thread("remove-barrier") {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    Thread.sleep(2000);
                    final int token = HandlerDemo.this.token;
                    if (token >= 0) {
                        final boolean b = invokeRemoveSyncBarrier(token);
                        Log.d(tag, "invoke remove syncBarrier " + (b ? "success" : "fail"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private int invokeSyncBarrier() {
        final Looper looper = handler.getLooper();
        final MessageQueue queue = looper.getQueue();
        final Method syncBarrierMethod;
        try {
            syncBarrierMethod = MessageQueue.class.getDeclaredMethod("postSyncBarrier", null);
            syncBarrierMethod.setAccessible(true);
            final Object invoke = syncBarrierMethod.invoke(queue);
            return (int) invoke;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean invokeRemoveSyncBarrier(int token) {
        final Looper looper = handler.getLooper();
        final MessageQueue queue = looper.getQueue();
        final Method syncBarrierMethod;
        try {
            syncBarrierMethod = MessageQueue.class.getDeclaredMethod("removeSyncBarrier", int.class);
            syncBarrierMethod.setAccessible(true);
            syncBarrierMethod.invoke(queue, token);
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}
