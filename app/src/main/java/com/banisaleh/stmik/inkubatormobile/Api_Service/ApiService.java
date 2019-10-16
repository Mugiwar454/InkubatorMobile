package com.banisaleh.stmik.inkubatormobile.Api_Service;

import android.content.Context;
import android.util.Log;

import com.banisaleh.stmik.inkubatormobile.Helper.AppConstant;
import com.banisaleh.stmik.inkubatormobile.Models.GetAddDevice;
import com.banisaleh.stmik.inkubatormobile.Models.GetDisable;
import com.banisaleh.stmik.inkubatormobile.Models.GetHistory;
import com.banisaleh.stmik.inkubatormobile.Models.GetHome;
import com.banisaleh.stmik.inkubatormobile.Models.GetNotif;
import com.banisaleh.stmik.inkubatormobile.Models.GetStart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    public static final String BASE_URL = AppConstant.BASE_URL;

    private String cookie;
    private GetHome gh;
    private GetDisable gd;

    public HttpURLConnection getHttpConnection(Context context, String endPoint) throws IOException {

        URL url = new URL(BASE_URL + endPoint);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setInstanceFollowRedirects(false);
        Log.d("inkubator mobile", "Current cookie:" + cookie + ", context:" + context);
        if (cookie == null && context != null) {
            this.cookie = FileHelper.getCookiesFromFile(context);
        }
        urlConnection.setRequestProperty("Cookie", cookie);
        urlConnection.setReadTimeout(AppConstant.READ_TIMEOUT);
        urlConnection.setConnectTimeout(AppConstant.CONNECTION_TIMEOUT);
        return urlConnection;
    }

    private String readResponse(HttpURLConnection connection) {
        try {
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            String data = "";
            while (line != null) {
                line = reader.readLine();
                data = data + line;

            }
            return data.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetStart getStart(Context context, String device_name) {
        try {
            HttpURLConnection connection = this.getHttpConnection(context, "Api_c/get_start");
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = ("{\"device_name\":" + "\"" + device_name + "\"" + "}");

            Log.i("Send", "Send" + data);
            OutputStream stream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            connection.connect();

            String response = this.readResponse(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Response", "Response" + connection.getResponseCode());

                Log.i("Response", "Response" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    GetStart res = new GetStart();
                    res.setStatus(jsonObject.getString("status"));
                    res.setMess(jsonObject.getString("mess"));
                    res.setId_devices(jsonObject.getString("id_devices"));
                    res.setDevice_name(jsonObject.getString("device_name"));
                    res.setTgl_mulai(jsonObject.getString("tgl_mulai"));
                    res.setTgl_selesai(jsonObject.getString("tgl_selesai"));
                    res.setDevice_status(jsonObject.getString("device_status"));
                    return res;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("Unknow Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetAddDevice getAddDevice(Context context, String device_name, String token) {
        try {
            HttpURLConnection connection = this.getHttpConnection(context, "Api_c/add_devices?device_name="+device_name+"&token="+token);
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("device_name", device_name);
                jsonObject.put("token", token);


            }catch (JSONException e) {
                e.printStackTrace();
            }

            String response = this.readResponse(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Response", "" + connection.getResponseCode());

                Log.i("Response", "DATA" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    GetAddDevice res = new GetAddDevice();
                    res.setStatus(jsonObject.getString("status"));
                    res.setMess(jsonObject.getString("mess"));
                    return res;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("Unknow Error");
            }

        } catch (NullPointerException  e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetStart LogoutApps(Context context) {
        try {
            HttpURLConnection connection = this.getHttpConnection(context, "Api_c/logout");
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Response Logout", "Response Logout " + connection.getResponseCode());
                String response = this.readResponse(connection);
                Log.i("Response Logout", "Response Logout" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    GetStart res = new GetStart();
                    res.setStatus(jsonObject.getString("status"));
                    res.setMess(jsonObject.getString("mess"));

                    return res;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("Unknow Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetDisable getDisable(Context context,String device_name) {
        try {
            HttpURLConnection connection = this.getHttpConnection(context, "Api_c/get_disable?device_name="+device_name);
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Response", "Response" + connection.getResponseCode());
                String response = this.readResponse(connection);
                Log.i("Response", "Response" + response);
                try {
                    JSONArray ja1 = new JSONArray(response);
                    for (int d = 0; d < ja1.length(); d++) {
                        JSONObject jo1 = ja1.getJSONObject(d);
                        gd = new GetDisable();
                        gd.setId_devices(jo1.getString("id_devices"));
                        gd.setDevice_name(jo1.getString("device_name"));
                        gd.setTgl_mulai(jo1.getString("tgl_mulai"));
                        gd.setTgl_selesai(jo1.getString("tgl_selesai"));
                        gd.setTotal(jo1.getString("total"));
                        gd.setStatus(jo1.getString("status"));
                        gd.setMess(jo1.getString("mess"));
                        return gd;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("Unknow Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetHome getHome(Context context) {
        try {
            HttpURLConnection connection = this.getHttpConnection(context, "Api_c/get_dht");
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();


            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Response", "Response getDataDashboard " + connection.getResponseCode());
                String response = this.readResponse(connection);
                Log.i("Response", "Response getDataDashboard" + response);
                try {
                    JSONArray js1 = new JSONArray(response);
                    for (int a1 = 0; a1 < js1.length(); a1++) {
                        JSONObject jo1 = js1.getJSONObject(a1);
                        gh = new GetHome();
                        gh.setTemp(jo1.getString("temp"));
                        gh.setHumidity(jo1.getString("humidity"));
                        gh.setDate(jo1.getString("date"));
                        gh.setLampu_1(jo1.getString("lampu_1"));
                        gh.setLampu_2(jo1.getString("lampu_2"));
                        gh.setRownum(jo1.getInt("rownum"));
                        return gh;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("Unknow Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GetHistory> getHistori(Context context, String device_name) {
        try {
            HttpURLConnection connection = this.getHttpConnection(context, "Api_c/get_history?device_name="+device_name);
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();
            Log.i("URL", "URL " + this.getHttpConnection(context, "Api_c/get_history?device_name=" +device_name));
            List<GetHistory> getHistori = new ArrayList<>();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Response", "Response " + connection.getResponseCode());
                String response = this.readResponse(connection);
                Log.i("Response", "Response" + response);
                try {
                    JSONArray js1 = new JSONArray(response);
                    for (int a = 0; a < js1.length(); a++) {
                        JSONObject j0 = js1.getJSONObject(a);
                        GetHistory his = new GetHistory();
                        his.setDate(j0.getString("date"));
                        his.setDevice_name(j0.getString("device_name"));
                        his.setTemp(j0.getString("temp"));
                        his.setHumidity(j0.getString("humidity"));
                        his.setLampu_1(j0.getString("lampu_1"));
                        his.setLampu_2(j0.getString("lampu_2"));
                        his.setRownum(Integer.valueOf(j0.getString("rownum")));

                        getHistori.add(his);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return getHistori;
            } else {
                throw new Exception("Unknow Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GetNotif> getNotif(Context context) {
        try {
            HttpURLConnection connection = this.getHttpConnection(context, "Api_c/get_notif");
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();
            Log.i("URL", "URL " + this.getHttpConnection(context, "Api_c/get_notif" ));
            List<GetNotif> getNotifs = new ArrayList<>();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Response", "Response " + connection.getResponseCode());
                String response = this.readResponse(connection);
                Log.i("Response", "Response" + response);
                try {
                    JSONArray js1 = new JSONArray(response);
                    for (int a = 0; a < js1.length(); a++) {
                        JSONObject j0 = js1.getJSONObject(a);
                        GetNotif his = new GetNotif();
                        his.setTgl_mess(j0.getString("tgl_mess"));
                        his.setPesan(j0.getString("pesan"));
                        his.setRownum(Integer.valueOf(j0.getString("rownum")));

                        getNotifs.add(his);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return getNotifs;
            } else {
                throw new Exception("Unknow Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
