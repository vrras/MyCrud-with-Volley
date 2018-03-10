package id.co.firas.crudvolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<DataSet> tujuanList;
    StringRequest request;
    RequestQueue requestQueue;
    ListView listView;
    ProgressBar bar;
    ListAdapterTujuan adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        bar = (ProgressBar) findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);

        tujuanList= new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        volleyget();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataSet tujuan = tujuanList.get(position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("id",tujuan.getId());
                intent.putExtra("tujuan",tujuan.getTujuan());
                startActivity(intent);
                finish();
            }
        });
    }

    public void addTujuan(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
        finish();
    }

    private void volleyget() {
        request = new StringRequest(Request.Method.GET, ConfigUrl.URL_TUJUAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                try {
                    bar.setVisibility(View.GONE);
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("tujuan");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject =array.getJSONObject(i);
                        DataSet tujuan = new DataSet();
                        tujuan.setId(jsonObject.getString("id"));
                        tujuan.setTujuan(jsonObject.getString("tujuan"));
                        tujuanList.add(tujuan);
                    }
                    adapter = new ListAdapterTujuan(MainActivity.this, R.layout.list_tujuan,tujuanList);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                tujuanList.clear();
                volleyget();
                //adapter.notifyDataSetChanged();
            }
        }
    }
}
