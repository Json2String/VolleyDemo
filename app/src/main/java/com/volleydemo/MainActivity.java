package com.volleydemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;
import static android.R.attr.width;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private static final String url = "http://192.168.0.101:8080/student.json";
    private ImageView mImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = (ImageView) findViewById(R.id.img);

        mContext = this;

        /**
         * volley请求三步曲
         * 1.创建对应的volley请求对象
         * 2.创建请求队列
         * 3.把请求对象添加到请求队列中去，发起请求
         */
    }

    public void stringRequest(View view) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                showToast("成功请求" + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(mContext, "请求失败" + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                showLog("失败:" + volleyError.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    public void jsonObjectRequest(View view) {
        JSONObject json = null;
        JsonObjectRequest request = new JsonObjectRequest(url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                showToast("请求成功" + jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showLog("请求失败" + volleyError.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(request);
    }

    public void jsonArrayRequest(View view) {
        String arrayUrl = "http://192.168.0.101:8080/Json/students";
        JsonArrayRequest request = new JsonArrayRequest(arrayUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.optJSONObject(i);
                    String name = json.optString("name");

                    showLog("i=" + i + ",name:" + name);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(request);
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, " " + msg, Toast.LENGTH_SHORT).show();
    }

    public void showLog(String msg) {
        Log.d("result", " " + msg);
    }

    public void imageRequest(View view) {

        String imgUrl = "http://192.168.0.101:8080/a.jpg";

        int maxWidth = 0;//0显示默认大小，最大宽度，单位px，大于0大图压缩，下同，volley内部已经处理大图压缩
        int maxHeight = 0;
        Bitmap.Config decodeConfig = Bitmap.Config.RGB_565;

        ImageRequest request = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            //成功回调
            @Override
            public void onResponse(Bitmap bitmap) {
                mImgView.setImageBitmap(bitmap);
            }
        }, maxWidth, maxHeight, decodeConfig, new Response.ErrorListener() {
            //失败回调
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(request);
        /**
         * volley三部曲
         * 1.创建请求对象
         * 2.创建请求队列
         * 3.添加请求对象到请求队列里面，然后发起请求
         *
         */

    }
}
