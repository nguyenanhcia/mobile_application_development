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

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by NguyenAnh on 3/17/2018.
 */

public class FragmentGetKm extends Fragment {
    private EditText longitude1;
    private EditText latitude1;
    private EditText longitude2;
    private EditText latitude2;

    private Button getKm;
    private TextView result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_getkm, container,false);

        latitude1 = view.findViewById(R.id.latitude1);
        longitude1 = view.findViewById(R.id.longitude1);

        latitude2 = view.findViewById(R.id.latitude2);
        longitude2 = view.findViewById(R.id.longitude2);

        getKm = view.findViewById(R.id.getkm);
        result = view.findViewById(R.id.result);

        getKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!latitude1.getText().toString().equals("") && !longitude1.getText().toString().equals("") && !latitude2.getText().toString().equals("") && !longitude2.getText().toString().equals("")){
                    String url = "https://fir-test-43bd7.firebaseapp.com/latlng?lat1=" + latitude1.getText().toString() + "&long1=" + longitude1.getText().toString() + "&lat2=" + latitude2.getText().toString() + "&long2=" + longitude2.getText().toString();
                    FragmentGetAdress.getAdressF(url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            result.setText("Không thể lấy được dữ liệu");
                        }

                        @Override
                        public void onNext(String s) {
                            result.setText("  " + s + " Km");
                        }
                    });
                }
            }
        });

        return view;
    }
}
