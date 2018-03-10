package id.co.firas.crudvolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Firas Luthfi on 1/17/2018.
 */

public class DetailActivity extends AppCompatActivity {
    static TextView id;
    TextView tujuan;
    RequestQueue requestQueue;
    StringRequest request;
    private String paramId;
    private String paramTujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        id = (TextView) findViewById(R.id.detail_id);
        tujuan = (TextView) findViewById(R.id.detail_tujuan);

        Intent intent = getIntent();
        this.paramId = intent.getStringExtra("id");
        this.paramTujuan = intent.getStringExtra("tujuan");

        id.setText("ID :    " + intent.getStringExtra("id"));
        tujuan.setText("Tujuan :  " + intent.getStringExtra("tujuan"));

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,UpdateActivity.class);
                intent.putExtra("id",paramId);
                intent.putExtra("tujuan",paramTujuan);
                startActivity(intent);
                finish();

//                final AlertDialog.Builder alert = new AlertDialog.Builder(DetailActivity.this);
//                alert.setTitle("Make a Call?")
//                        .setMessage("Do you want to make a call?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent1 = new Intent(Intent.ACTION_DIAL);
//                                intent1.setData(Uri.parse("tel:"+intent.getStringExtra("phone")));
//                                startActivity(intent1);
//                            }
//                        })
//                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                alert.setCancelable(true);
//                            }
//                        });
//                alert.create();
//                alert.show();
            }
        });
    }

    public void deleteMethod(View view) {

        String url="http://192.168.43.49:8080/Cbapi/tujuan/"+ this.paramId;
        deleteData(url);
    }

    public void deleteData(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
                Toast.makeText(DetailActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }
}
