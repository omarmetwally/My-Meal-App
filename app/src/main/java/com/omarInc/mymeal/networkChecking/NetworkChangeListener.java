package com.omarInc.mymeal.networkChecking;

public interface NetworkChangeListener {
    void onNetworkChange(boolean isConnected, NetworkChangeActionListener actionListener);
}
