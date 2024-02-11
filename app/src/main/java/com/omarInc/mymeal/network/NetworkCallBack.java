package com.omarInc.mymeal.network;




import java.util.List;

public interface NetworkCallBack<T> {
    void onSuccessResult(T result);
    void onFailureResult(String errorMsg);
}