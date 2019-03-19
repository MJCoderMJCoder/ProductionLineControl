package com.lzf.productionlinecontrol.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by MJCoder on 2019-02-28.
 */

public class RebootReceiver extends BroadcastReceiver {

    private final String ACTION_REBOOT = "android.intent.action.REBOOT";

    public RebootReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_REBOOT.equals(intent.getAction())) {
            //            Toast.makeText(context, "PAD重启", Toast.LENGTH_LONG).show();
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
