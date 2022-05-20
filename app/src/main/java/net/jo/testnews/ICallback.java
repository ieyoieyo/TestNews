package net.jo.testnews;


public interface ICallback<T> {

    void onSuccess(T data);
    void onError(String msg);
    void onFailure(String msg);

}
