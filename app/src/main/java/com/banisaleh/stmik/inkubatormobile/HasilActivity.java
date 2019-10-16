package com.banisaleh.stmik.inkubatormobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.banisaleh.stmik.inkubatormobile.Helper.AppConstant;
import com.banisaleh.stmik.inkubatormobile.Models.GetStart;

public class HasilActivity extends AppCompatActivity {
    Bundle g;
    String tgl_awal, tgl_selesai, totalH;
    private TextView tvAwal;
    private TextView tvSelesai;
    private TextView tvTotal;
    private Button bLogout;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        tvAwal = (TextView) findViewById(R.id.tglMulai);
        tvSelesai = (TextView) findViewById(R.id.tglSelesai);
        tvTotal = (TextView) findViewById(R.id.totalPenetasan);

        sharedPreferences = getSharedPreferences(AppConstant.myPreferenced, MODE_PRIVATE);

        Intent in = getIntent();
        g = in.getExtras();

        if (g != null) {
            tgl_awal = (String) g.get("tgl_mulai");
            tgl_selesai = (String) g.get("tgl_selesai");
            totalH = (String) g.get("total");

            tvAwal.setText("" + tgl_awal);
            tvSelesai.setText("" + tgl_selesai);
            tvTotal.setText("" + totalH);

        } else {
            tvAwal.setText("Not Respone");
            tvSelesai.setText("Not Respone");
            tvTotal.setText("Not Respone");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                new Logout().execute();
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public class Logout extends AsyncTask<String, Void, GetStart> {

        ProgressDialog pLogin = new ProgressDialog(HasilActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pLogin.setTitle("\tPlease Wait");
            pLogin.setMessage("\t Check your connection");
            pLogin.setCancelable(false);
            pLogin.show();
        }

        @Override
        protected GetStart doInBackground(String... strings) {

            try {
                return AppConstant.getServiceApi().LogoutApps(HasilActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(GetStart responLogout) {
            super.onPostExecute(responLogout);
            pLogin.dismiss();

            if (responLogout != null) {
                if (responLogout.getStatus().equalsIgnoreCase("success")) {
                    Intent intent = new Intent(HasilActivity.this, StartActivity.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("id_device");
                    editor.remove("device_name");
                    editor.remove("tgl_mulai");
                    editor.remove("tgl_selesai");
                    editor.remove("device_status");
                    editor.remove("mess");
                    editor.remove("status");
                    editor.commit();
                    editor.apply();

                } else {
                    Intent intent = new Intent(HasilActivity.this, HasilActivity.class);
                    startActivity(intent);
                }
//
            } else {

            }
        }
    }
}
