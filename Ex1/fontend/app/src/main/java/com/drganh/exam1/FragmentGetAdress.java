package com.drganh.exam1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by NguyenAnh on 3/17/2018.
 */

public class FragmentGetAdress extends Fragment {
    private EditText latitute;
    private EditText longtitute;
    private Button getAddress;
    private TextView result;
    private static InputStream is = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container,false);

        latitute = view.findViewById(R.id.latitude);
        longtitute = view.findViewById(R.id.longitude);
        getAddress = view.findViewById(R.id.getaddress);
        result = view.findViewById(R.id.result);

        getAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!latitute.getText().toString().equals("") && !longtitute.getText().toString().equals("")) {
                    String url = "https://fir-test-43bd7.firebaseapp.com/xxx?lat=" + latitute.getText().toString() + "&long=" + longtitute.getText().toString() + "";
                    getAdressF(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            result.setText("Không thể lấy được dữ liệu");
                        }

                        @Override
                        public void onNext(String s) {
                            result.setText(s);
                        }
                    });
                }
            }
        });

        return view;
    }

    public static Observable<String> getAdressF(final String url){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                StringBuilder builder = new StringBuilder();
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    
                    is = response.body().source().inputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    subscriber.onNext(builder.toString());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
