package com.example.recyclerviewb;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton _addButton, refreshButton;
    private RecyclerView _recyclerView1;
    private TextView _txtMahasiswaCount, _txtSearch;
    private MahasiswaAdapter ma;
    private ImageButton _btnSearch;
    private List<MahasiswaModel> mahasiswaModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _recyclerView1 = findViewById(R.id.recyclerView1);
        _txtMahasiswaCount = findViewById(R.id.txtMahasiswaCount);


        initAddButton();
        loadRecyclerView();
        initRefreshButton();
        initSearchButton();

    }

    private void initSearchButton() {
        _txtSearch = findViewById(R.id.txtSearch);
        _txtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String filterText = _txtSearch.getText().toString();
                if (!filterText.isEmpty()){
                    fii(filterText);
                }
                else
                    loadRecyclerView();
                return false;

            }
        });
    }

    public void filter(String text) {
        List<MahasiswaModel> filteredList = new ArrayList<>();

        for (MahasiswaModel item: mahasiswaModelList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            ma.filterList(filteredList);
        }
    }

    private void initAddButton() {
        _addButton = findViewById(R.id.addButton);

        _addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), AddMahasiswaActivity.class);
                startActivity(intent);
                loadRecyclerView();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadRecyclerView();
    }

    private void loadRecyclerView() {
        AsyncHttpClient ahc = new AsyncHttpClient();
        String url = "https://stmikpontianak.net/011100862/tampilMahasiswa.php";

        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson g = new Gson();
                List<MahasiswaModel> mahasiswaModelList = g.fromJson(new String(responseBody), new TypeToken<List<MahasiswaModel>>(){}.getType());

                RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
                _recyclerView1.setLayoutManager(lm);

                MahasiswaAdapter ma = new MahasiswaAdapter(mahasiswaModelList);
                _recyclerView1.setAdapter(ma);

                String mahasiswaCount = "Total Mahasiswa : " + ma.getItemCount();
                _txtMahasiswaCount.setText(mahasiswaCount);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRefreshButton() {
        _refreshButton = findViewById(R.id.refreshButton);
        _refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRecyclerView();
            }
        });

    }
}