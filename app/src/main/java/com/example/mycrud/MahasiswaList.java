//package com.example.mycrud;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class MahasiswaList extends AppCompatActivity {
//
//    private static final int CODE_GET_REQUEST = 1024;
//    private static final int CODE_POST_REQUEST = 1025;
//
//    EditText editTextMahasiswaId, editTextNama, editTextAlamat;
//    ProgressBar progressBar;
//    ListView listView;
//    Button buttonAddUpdate;
//
//    List<Mahasiswa> mahasiswaList;
//    boolean isUpdating = false;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        editTextMahasiswaId=(EditText) findViewById(R.id.editTextMahasiswaId);
//        editTextNama=(EditText) findViewById(R.id.editTextNama);
//        editTextAlamat=(EditText) findViewById(R.id.editTextAlamat);
//        buttonAddUpdate=(Button) findViewById(R.id.buttonAddUpdate);
//        progressBar=(ProgressBar) findViewById(R.id.progressBar);
//        listView=(ListView) findViewById(R.id.listViewMahasiswa);
//        mahasiswaList = new ArrayList<>();
//        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isUpdating){
//
//                }else{
//                    updateMahasiswa();
//                }
//            }
//        });
//        readMahasiswa();
//    }
//
//    private void readMahasiswa() {
//        MahasiswaList.PerformNetworkRequest request = new
//                MahasiswaList.PerformNetworkRequest(ApiMahasiswa.URL_R_MHS, null, CODE_GET_REQUEST);
//        request.execute();
//    }
//
//    public void refreshMahasiswaList(JSONArray mahasiswa) throws JSONException {
//        mahasiswaList.clear();
//        for (int i = 0; i < mahasiswa.length(); i++) {
//
//            JSONObject obj = mahasiswa.getJSONObject(i);
//            mahasiswaList.add(new Mahasiswa(
//                    obj.getInt("id"),
//                    obj.getString("nama"),
//                    obj.getString("alamat")
//            ));
//        }
//        MahasiswaList.MahasiswaAdapter adapter = new MahasiswaList.MahasiswaAdapter(mahasiswaList);
//        listView.setAdapter(adapter);
//    }
//
//    private void updateMahasiswa(){
//        String id = editTextMahasiswaId.getText().toString();
//        String nama = editTextNama.getText().toString().trim();
//        String alamat = editTextAlamat.getText().toString().trim();
//        if (TextUtils.isEmpty(nama)) {
//            editTextNama.setError("Please enter nama");
//            editTextNama.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(alamat)) {
//            editTextAlamat.setError("Please enter alamat");
//            editTextAlamat.requestFocus();
//            return;
//        }
//        HashMap<String, String> params = new HashMap<>();
//        params.put("id", id);
//        params.put("nama", nama);
//        params.put("alamat", alamat);
//        MahasiswaList.PerformNetworkRequest request = new MahasiswaList.PerformNetworkRequest(ApiMahasiswa.URL_U_MHS,
//                params, CODE_POST_REQUEST);
//        request.execute();
//        buttonAddUpdate.setText("Add");
//        editTextNama.setText("");
//        editTextAlamat.setText("");
//        isUpdating = false;
//    }
//
//    private void deleteMahasiswa(int id) {
//        MahasiswaList.PerformNetworkRequest request = new
//                MahasiswaList.PerformNetworkRequest(ApiMahasiswa.URL_D_MHS + id, null,
//                CODE_GET_REQUEST);
//        request.execute();
//    }
//
//    //ASYNC TASK//
//    @SuppressLint("StaticFieldLeak")
//    private class PerformNetworkRequest extends AsyncTask<Void,Void, String> {
//        String url;
//        HashMap<String, String> params;
//        int requestCode;
//        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
//            this.url = url;
//            this.params = params;
//            this.requestCode = requestCode;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            progressBar.setVisibility(View.GONE);
//            try {
//                JSONObject object = new JSONObject(s);
//                if (!object.getBoolean("error")) {
//                    Toast.makeText(getApplicationContext(),
//                            object.getString("message"), Toast.LENGTH_LONG).show();
//                    refreshMahasiswaList(object.getJSONArray("mahasiswa"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            RequestHandler requestHandler = new RequestHandler();
//            if (requestCode == CODE_POST_REQUEST)
//                return requestHandler.sendPostRequest(url, params);
//            if (requestCode == CODE_GET_REQUEST)
//                return requestHandler.sendGetRequest(url);
//            return null;
//        }
//    }
//
//
//    //INNER CLASS Mahasiswa Adapter//
//    public class MahasiswaAdapter extends ArrayAdapter<Mahasiswa> {
//        List<Mahasiswa> mahasiswaList;
//        public MahasiswaAdapter(List<Mahasiswa> mahasiswaList) {
//            super(MahasiswaList.this, R.layout.layout_mahasiswa_list,
//                    mahasiswaList);
//            this.mahasiswaList=mahasiswaList;
//        }
//        public View getView(int position, View convertView, ViewGroup parent){
//            LayoutInflater inflater = getLayoutInflater();
//            View listViewItem = inflater.inflate(R.layout.layout_mahasiswa_list,
//                    null, true);
//            TextView textViewNama = listViewItem.findViewById(R.id.textViewNama);
//            TextView textViewUpdate =
//                    listViewItem.findViewById(R.id.textViewUpdate);
//            TextView textViewDelete =
//                    listViewItem.findViewById(R.id.textViewDelete);
//            final Mahasiswa mahasiswa = mahasiswaList.get(position);
//            textViewNama.setText(mahasiswa.getNama());
//            textViewUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    boolean isUpdating = true;
//                    editTextMahasiswaId.setText(String.valueOf(mahasiswa.getId()));
//                    editTextNama.setText(mahasiswa.getNama());
//                    editTextAlamat.setText(mahasiswa.getAlamat());
//                    buttonAddUpdate.setText("Update");
//                }
//            });
//            textViewDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder builder = new
//                            AlertDialog.Builder(MahasiswaList.this);
//                    builder.setTitle("Delete " + mahasiswa.getNama())
//                            .setMessage("Are you sure you want to delete it?")
//                            .setPositiveButton(android.R.string.yes, new
//                                    DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int
//                                                which) {
//                                            //deleteMahasiswa(mahasiswa.getId());
//                                        }
//                                    })
//                            .setNegativeButton(android.R.string.no, new
//                                    DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int
//                                                which) {
//                                        }
//                                    })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                }
//            });
//            return listViewItem;
//        }
//    }
//}
