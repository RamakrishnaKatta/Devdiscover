package com.example.zapyle.devdiscover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

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

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    ArrayList<String> Imagearray= new ArrayList<String>();
   ListView listView;
    GridView gridView;
    customAdapter adapter;
    Button button;
    private boolean state=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue= Volley.newRequestQueue(getApplicationContext());


        listView=(ListView)findViewById(R.id.list_product);
        gridView=(GridView) findViewById(R.id.list_items);
        button=(Button)findViewById(R.id.imageButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(state) {
                    state = false;
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    adapter = new customAdapter(MainActivity.this, Imagearray);
                    gridView.setAdapter(adapter);
                }else {
                    state=true;
                    adapter=new customAdapter(MainActivity.this,Imagearray);
                    listView.setAdapter(adapter);
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }

            }
        });


        getData();

    }

    private void getData() {
        final String url="http://dev.zapyle.com/discover/";
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {

                    JSONObject resp = new JSONObject(response);
                    JSONArray data = resp.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject id = data.getJSONObject(i);
                        JSONObject content = id.getJSONObject("content_data");
                        String discover_type = content.getString("discover_type");
                        if (discover_type.equals("banner")) {
                            JSONObject discover_data = content.getJSONObject("discover_data");
                            String url1 = discover_data.getString("image");
                            Imagearray.add(discover_data.getString("image"));
                        } else if (discover_type.equals("closet")) {
                            JSONObject discover_data1=content.getJSONObject("discover_data");
                            JSONArray product=discover_data1.getJSONArray("product");
                            for (int j=0;j<product.length();j++){
                                JSONObject image=product.getJSONObject(j);
                                String url2=image.getString("image");
                                Imagearray.add(image.getString("image"));
                                System.out.println("product" + product.getJSONObject(j));

                            }


                        }
                        else if (discover_type.equals("product_collection")){
                            JSONObject discover_data2=content.getJSONObject("discover_data");
                            JSONArray product1=discover_data2.getJSONArray("product");
                            JSONObject image=product1.getJSONObject(0);
                            String url3=image.getString("image");
                            Imagearray.add(image.getString("image"));
                        }

                        else if (discover_type.equals("custom_collection")){
                            JSONObject discover_data3=content.getJSONObject("discover_data");
                            JSONArray collection=discover_data3.getJSONArray("collection");
                            for (int k=0;k<collection.length();k++){
                                JSONObject images=collection.getJSONObject(k);
                                String url4=images.getString("image");
                                Imagearray.add(images.getString("image"));
                            }
                        }
                        else if (discover_type.equals("generic")){
                            JSONObject discover_data4=content.getJSONObject("discover_data");
                            String url5=discover_data4.getString("image");
                            Imagearray.add(discover_data4.getString("image"));
                        }
                    }


                    for (int i=0;i<Imagearray.size();i++){
                        System.out.println("URL:"+Imagearray.get(i));
                    }
//                    adapter = new customAdapter(MainActivity.this,Imagearray);
//                  gridView=(GridView) findViewById(R.id.list_items);
//                    gridView.setAdapter(adapter);
//

//                    adapter=new customAdapter(MainActivity.this,Imagearray);
////                    listView=(ListView)findViewById(R.id.list_product);
//                    listView.setAdapter(adapter);
//


//                    button=(Button)findViewById(R.id.imageButton);
//                    button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            adapter = new customAdapter(MainActivity.this,Imagearray);
//                            gridView=(GridView) findViewById(R.id.list_item);
//                            gridView.setAdapter(adapter);

//                        }
//                    });




                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("error"+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Getdata inside error__"+error);
            }
        });


        requestQueue.add(stringRequest);
    }
}