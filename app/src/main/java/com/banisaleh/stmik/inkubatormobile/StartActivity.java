package com.banisaleh.stmik.inkubatormobile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.banisaleh.stmik.inkubatormobile.Helper.AppConstant;
import com.banisaleh.stmik.inkubatormobile.Models.GetAddDevice;
import com.banisaleh.stmik.inkubatormobile.Models.GetStart;
import com.google.firebase.iid.FirebaseInstanceId;

public class StartActivity extends AppCompatActivity {
    private Button btn_start;
    private EditText uname;
    private TextView addDevice;

    private SharedPreferences sharedPreferences;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    String device_name,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sharedPreferences = getSharedPreferences(AppConstant.myPreferenced, MODE_PRIVATE);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();

            }
        });

        addDevice=(TextView)findViewById(R.id.addDevice);
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDaftar();
            }
        });

    }

    private void DialogDaftar() {
        dialog = new AlertDialog.Builder(StartActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.device_name, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.icon);
        dialog.setTitle("Masukan nama device");

        uname = (EditText) dialogView.findViewById(R.id.username);

        kosong();

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AddDevice().execute();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(StartActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.device_name, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.icon);
        dialog.setTitle("Masukan nama device");

        uname = (EditText) dialogView.findViewById(R.id.username);

        kosong();

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new checkDevice().execute();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void kosong() {
        uname.setText(null);
    }

    public class checkDevice extends AsyncTask<String, Void, GetStart> {

        ProgressDialog pLogin = new ProgressDialog(StartActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pLogin.setTitle("\tPlease Wait");
            pLogin.setMessage("\t Check your Device Name");
            pLogin.setCancelable(false);
            pLogin.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected GetStart doInBackground(String... strings) {
            device_name = uname.getText().toString();
            try {
                return AppConstant.getServiceApi().getStart(StartActivity.this, device_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("ApplySharedPref")
        @Override
        protected void onPostExecute(GetStart respon) {
            super.onPostExecute(respon);
            pLogin.dismiss();
            if (respon != null) {
                if (respon.getStatus().equalsIgnoreCase("success")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id_devices", respon.getId_devices());
                    editor.putString("device_name", respon.getDevice_name());
                    editor.putString("tgl_mulai", respon.getTgl_mulai());
                    editor.putString("tgl_selesai", respon.getTgl_selesai());
                    editor.putString("mess", respon.getStatus());
                    editor.putString("status",respon.getStatus());
                    editor.putString("device_status", respon.getDevice_status());
                    editor.commit();
                    editor.apply();
                    Intent in = new Intent(StartActivity.this, MainActivity.class);
                    in.putExtra("id_devices", respon.getId_devices());
                    in.putExtra("device_name", respon.getDevice_name());
                    in.putExtra("tgl_mulai", respon.getTgl_mulai());
                    in.putExtra("tgl_selesai", respon.getTgl_selesai());
                    in.putExtra("device_status",respon.getDevice_status());
                    in.putExtra("status",respon.getStatus());
                    in.putExtra("mess",respon.getMess());

                    startActivity(in);

                    Toast.makeText(StartActivity.this, "" + respon.getMess(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StartActivity.this, "" + respon.getMess(), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(StartActivity.this, "Please Check Koneksi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class AddDevice extends AsyncTask<String, Void, GetAddDevice> {

        ProgressDialog pLogin = new ProgressDialog(StartActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pLogin.setTitle("\tPlease Wait");
            pLogin.setMessage("\t Check your Device Name");
            pLogin.setCancelable(false);
            pLogin.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected GetAddDevice doInBackground(String... strings) {
            device_name = uname.getText().toString();
            token= FirebaseInstanceId.getInstance().getToken();
            try {
                return AppConstant.getServiceApi().getAddDevice(StartActivity.this, device_name,token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("ApplySharedPref")
        @Override
        protected void onPostExecute(GetAddDevice respon) {
            super.onPostExecute(respon);
            pLogin.dismiss();
            if (respon != null) {
                if (respon.getStatus().equalsIgnoreCase("sukses")) {
                    Toast.makeText(StartActivity.this, "" + respon.getMess(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(StartActivity.this, "" + respon.getMess(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(StartActivity.this, "Gagal Insert", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
