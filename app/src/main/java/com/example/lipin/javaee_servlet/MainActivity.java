package com.example.lipin.javaee_servlet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.realname)
    EditText realname;
    @BindView(R.id.mesg)
    TextView mesg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    //這為GET方法
    public void add(View view) {
        String url = "http://192.168.1.123:8080/JAVAEE/brad59.jsp?account=" +
                account.getText().toString() + "&" +
                "passwd=" + password.getText().toString() + "&" +
                "realname=" + realname.getText().toString();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //在這裡的this= new StringRequest物件實體
                        //所以需要MainActivity.this
                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("brad", error.toString());
                    }
                }
        );
        MainApp.queue.add(request);
    }

    //此為POST方法
    public void add2(View view) {
        String url = "http://192.168.1.123:8080/JAVAEE/brad59.jsp";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //在這裡的this= new StringRequest物件實體
                        //所以需要MainActivity.this
                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("brad", error.toString());
                    }
                }
        ) {
            //如果想要實作POST Request那麼必須自己覆寫getParams這個method
            //將抓下來的資料傳地出去
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("account", account.getText().toString());
                params.put("passwd", password.getText().toString());
                params.put("realname", realname.getText().toString());

                return params;
            }
        };
        MainApp.queue.add(request);
    }

    public void test1(View view) {
        JsonArrayRequest request = new JsonArrayRequest(
                "http://192.168.1.123:8080/JAVAEE/brad24",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("brad1",error.toString());
                    }
                }
        );

        MainApp.queue.add(request);
    }
    //抓取json方法
    private void parseJSON(JSONArray jsonArray){
        try {
            mesg.setText("");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject row = jsonArray.getJSONObject(i);
                mesg.append(row.getString("id")+":");
                mesg.append(row.getString("account")+":");
                mesg.append(row.getString("passwd")+":");
                mesg.append(row.getString("realname")+"\n");
            }

        }catch (Exception e){
            Log.v("brad",e.toString());
        }
    }
}
