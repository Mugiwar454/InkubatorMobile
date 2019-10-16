package com.banisaleh.stmik.inkubatormobile.Api_Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class FirebaseInstance extends FirebaseInstanceIdService {
    String device_name;
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
        Log.d(TAG, "token: " + token);
    }

    public void caritoken(){
        onTokenRefresh();
    }

    private void registerToken(String token){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token",token)
                .build();
//
//        Request request = new Request.Builder()
//                .url(AppConstant.BASE_URL + "Api_c/reg_token?token_device="+token )
//                .post(body)
//                .build();

//        try {
//            client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
