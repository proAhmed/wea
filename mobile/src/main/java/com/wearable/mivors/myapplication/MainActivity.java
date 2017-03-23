package com.wearable.mivors.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.wearable.mivors.myapplication.adapter.UserAdapter;
import com.wearable.mivors.myapplication.connector.GetContactByEmail;
import com.wearable.mivors.myapplication.connector.GetData;
import com.wearable.mivors.myapplication.connector.UpdateStatus;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.controller.OnUserClick;
import com.wearable.mivors.myapplication.controller.StoreData;
import com.wearable.mivors.myapplication.controller.Utility;
import com.wearable.mivors.myapplication.model.MangerModel;
import com.wearable.mivors.myapplication.model.Row;
import com.wearable.mivors.myapplication.model.UserData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnUserClick {

    RecyclerView recycle;
    ArrayList<UserData> arrayLists;
    OnUserClick onUserClick;
    OnLoadingComplete onLoadingComplete;
    ArrayList<Row> arrayList;
    ArrayList<String> arrayListString;
    Utility utility;
    static final Integer WRITE_EXST = 0x3;

    int poss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////////////// horizontal recycleview to navigate horizontal
        recycle = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycle.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayListString = new ArrayList<>();
        utility = new Utility();
        utility = new Utility(this);
        utility.startAtTime();
        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXST);

        onUserClick = this;
        try {
            if (Utility.isNetworkConnected(MainActivity.this)) {
                onLoadingComplete = new OnLoadingComplete() {
                    @Override
                    public void onSuccess(Object object) {
                        final ArrayList<Integer> arrayListss = (ArrayList<Integer>) object;

                        onLoadingComplete = new OnLoadingComplete() {
                            @Override
                            public void onSuccess(Object object) {
                                arrayList = (ArrayList<Row>) object;

                                new StoreData(MainActivity.this).setUserId(arrayList.get(0).getPrimaryId());
                                Log.d("pppp", utility.formatDate(arrayList).size() + "   //  " + arrayList.get(poss).getCreated());
                                if (utility.formatDate(arrayList).size() > 0) {
                                    UserAdapter userAdapter = new UserAdapter(MainActivity.this, utility.formatDate(arrayList), onUserClick);
                                    recycle.setAdapter(userAdapter);
                                    userAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "No attendance violate ", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure() {

                            }
                        };
                        try {
                            new GetData(MainActivity.this, onLoadingComplete, arrayListss.get(0)).execute();
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onFailure() {

                    }
                };
                new GetContactByEmail(MainActivity.this, onLoadingComplete).execute(new StoreData(MainActivity.this).getUserEmail());
            } else {
                Toast.makeText(MainActivity.this, "Please connect to Internet ", Toast.LENGTH_SHORT).show();

            }
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(MainActivity.this, "No attendance violate ", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "No attendance violate ", Toast.LENGTH_SHORT).show();
        }


// execute this when the downloader must be fired


    }

    @Override
    public void reject(int pos) {
        try {
            if (Utility.isNetworkConnected(MainActivity.this)) {
                Toast.makeText(MainActivity.this, "Rejected", Toast.LENGTH_LONG).show();
                int primaryId = utility.formatDate(arrayList).get(pos).getPrimaryId();
                new UpdateStatus(MainActivity.this, onLoadingComplete, utility.formatDate(arrayList).get(pos).getId()).execute(new MangerModel(primaryId, 1));
            }
        } catch (Exception e) {
            Log.d("eeeooo", e.toString());
        }
        poss = poss + 1;
    }

    @Override
    public void accept(int pos) {

            if (Utility.isNetworkConnected(MainActivity.this)) {
                Toast.makeText(MainActivity.this, "Approved", Toast.LENGTH_LONG).show();
                int primaryId = utility.formatDate(arrayList).get(pos).getPrimaryId();
                new UpdateStatus(MainActivity.this, onLoadingComplete, utility.formatDate(arrayList).get(pos).getId()).execute(new MangerModel(primaryId, 2));
            }

        poss = poss + 1;
    }


    public class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {

//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://mivors--tst1.custhelp.com/cc/ImageCustomController/getProfileImage/c_id/107"));
//                request.setTitle("Some title");
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                Log.d("ooo", "" + connection.getResponseMessage() + "   " + connection.getRequestMethod());
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/file_name.png");
                Log.d("ooo", "" + fileLength);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                Log.d("ooo", "" + e.toString());

                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {





        }
    }
}