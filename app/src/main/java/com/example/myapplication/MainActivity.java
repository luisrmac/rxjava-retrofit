package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;

import com.example.myapplication.model.RandomResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_TAG";
    private RandomAPI randomAPI;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomAPI = prepareRandomAPI();
        subscribeNetworkCall();
    }

    private void subscribeNetworkCall() {
        /// CompositeDisposables
        randomAPI.getRandomUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RandomResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        ///
                    }

                    @Override
                    public void onSuccess(RandomResponse response) {
                        handleResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
//        randomAPI.getRandomUser()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<RandomResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        ///
//                    }
//
//                    @Override
//                    public void onNext(RandomResponse response) {
//                        handleResponse(response);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: ", e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: finished emitting data");
//                    }
//                });
    }

    private void handleResponse(RandomResponse response) {
        Log.d(TAG, "handleResponse: " + response);
    }

    private RandomAPI prepareRandomAPI() {
        if(retrofit == null) {
            retrofit = prepareRetrofit();
        }
        return retrofit.create(RandomAPI.class);
    }

    private Retrofit prepareRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    
}
