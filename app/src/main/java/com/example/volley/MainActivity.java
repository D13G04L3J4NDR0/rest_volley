package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText name,last,email,phone;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);

        name = findViewById(R.id.nombre);
        last = findViewById(R.id.apellido);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.telefono);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarServicio();
            }
        });

    }

    private void enviarServicio() {

        final ProgressDialog loading = ProgressDialog.show(this, "Espere ...", "Actualizando Datos", false, false);

        String url = "http://34.67.71.245/webhook/api/v1.php?action=contact_add";
        //String url = "http://localhost/webhook/api/v1.php?action=contact_add";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                    }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("name" , name.getText().toString());
                params.put("last", last.getText().toString());
                params.put("email", email.getText().toString());
                params.put("phone", phone.getText().toString());

                Log.e("PARAMS", params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
