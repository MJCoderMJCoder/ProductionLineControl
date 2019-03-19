package com.lzf.productionlinecontrol.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by MJCoder on 2019-02-28.
 */

public class UserPresentReceiver extends BroadcastReceiver {

    private final String ACTION_USER_PRESENT = "android.intent.action.USER_PRESENT";

    public UserPresentReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_USER_PRESENT.equals(intent.getAction())) {
            //            Toast.makeText(context, "解锁", Toast.LENGTH_LONG).show();
            //            context.startActivity(new Intent(context, IndexActivity.class));
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }
}
