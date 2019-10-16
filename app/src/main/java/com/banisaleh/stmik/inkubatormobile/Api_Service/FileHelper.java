package com.banisaleh.stmik.inkubatormobile.Api_Service;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    public static void saveCookies(String cookies, Context context) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("Inkubatorcookies.txt", Context.MODE_PRIVATE));
            osw.write(cookies);
            osw.close();
        } catch (IOException e) {
            Log.i("Exception", "File write failed: " + e.toString());
        }
    }

    public static void removeCookiesFile(Context context) {
        try {
            context.deleteFile("Inkubatorcookies.txt");
        } catch (Exception e) {
        }
    }

    public static String getCookiesFromFile(Context context) {
        Log.i("Inkubator", "Get cookie from file");
        String ret = null;

        try {
            InputStream inputStream = context.openFileInput("Inkubatorcookies.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (Exception e) {
        }
        Log.i("Inkubator", "Cookie:" + ret);
        return ret;
    }


    public static void saveMenuListStr(String menuListSrver, Context context) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("menumodel.txt", Context.MODE_PRIVATE));
            osw.write(menuListSrver);
            osw.close();
        } catch (Exception e) {
            Log.i("Exception", "File write failed: " + e.toString());
        }
    }




}
