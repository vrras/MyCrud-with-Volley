package id.co.firas.crudvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText id,tujuan;
    Button btn;
    RequestQueue requestQueue;
    StringRequest request;
    private static final String url="http://192.168.43.49:8080/Cbapi/tujuan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        id = (EditText) findViewById(R.id.ed_add_id);
        tujuan = (EditText) findViewById(R.id.ed_add_tujuan);
        btn= (Button) findViewById(R.id.btn_add);
        requestQueue = Volley.newRequestQueue(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(id.getText().toString(),tujuan.getText().toString());
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void addData(final String id, final String tujuan) {
        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    Log.d("addResponse",response);
                    Toast.makeText(AddActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("tujuan",tujuan);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
