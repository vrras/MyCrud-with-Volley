package id.co.firas.crudvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private EditText id, tujuan;
    String paramId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id = (EditText) findViewById(R.id.ed_id);
        tujuan = (EditText) findViewById(R.id.ed_tujuan);

        id.setEnabled(false);

        Intent intent = getIntent();

        this.paramId = intent.getStringExtra("id");
        id.setText(intent.getStringExtra("id"));
        tujuan.setText(intent.getStringExtra("tujuan"));
    }

    public void UpdateMethod(View view) {
        String url = "http://192.168.43.49:8080/Cbapi/tujuan/" + this.paramId;
        updateData(url);
    }

    public void updateData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LOG_VOLLEY", response);
                Toast.makeText(UpdateActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOG_VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tujuan", tujuan.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
