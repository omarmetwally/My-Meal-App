package com.omarInc.mymeal.networkChecking;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.provider.Settings;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class NetworkChangeManager {
    private static NetworkChangeManager instance;
    private final Set<NetworkChangeListener> listeners = new HashSet<>();
    private boolean isRegistered = false;
    private BroadcastReceiver networkChangeReceiver;
    private WeakReference<Context> contextReference;


    public static synchronized NetworkChangeManager getInstance() {
        if (instance == null) {
            instance = new NetworkChangeManager();
        }
        return instance;
    }
    public void setContext(Context context) {
        this.contextReference = new WeakReference<>(context);
    }

    private NetworkChangeManager() {
        networkChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                    boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                    notifyListeners(!noConnectivity);
                }
            }
        };
    }

    public void registerForNetworkChange(Context context) {
        if (!isRegistered) {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(networkChangeReceiver, filter);
            isRegistered = true;
        }
    }

    public void unregisterForNetworkChange(Context context) {
        if (isRegistered) {
            context.unregisterReceiver(networkChangeReceiver);
            isRegistered = false;
        }
    }

    public void addListener(NetworkChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(NetworkChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(boolean isConnected) {
        NetworkChangeActionListener actionListener = this::handleAction;
        for (NetworkChangeListener listener : listeners) {
            listener.onNetworkChange(isConnected, actionListener);
        }
    }
    private void handleAction() {
        Context context = contextReference.get(); // Get the context from the WeakReference
        if (context != null) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            context.startActivity(intent);
        } else {
            // Context is no longer available
//            Log.e("NetworkManager", "Context is null, cannot perform action.");
        }
    }
}
