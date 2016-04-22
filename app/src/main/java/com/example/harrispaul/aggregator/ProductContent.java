package com.example.harrispaul.aggregator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Harrispaul on 3/28/2016.
 */
public class ProductContent extends Activity {

    ListView list;
    String jsonStr;
    ArrayList<ItemContent> array = new ArrayList<ItemContent>();
    ImageButton enter;
    EditText search;
    ProgressBar progressBar;
    SingleProductCustomBaseAdapter adapter;
    Bitmap bitmap;
    Button home,help,deals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_product);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Object o;
        o = getIntent().getSerializableExtra("product");
        final ItemContent o1 = (ItemContent) o;

        adapter = new SingleProductCustomBaseAdapter(this, array);

        list = (ListView) findViewById(R.id.single_product_list);

        search = (EditText) findViewById(R.id.search_text);
        home=(Button)findViewById(R.id.home);
        help=(Button)findViewById(R.id.help);
        deals=(Button)findViewById(R.id.deals);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        list.setAdapter(adapter);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String[] searchString = new String[1];
                    searchString[0] = search.getText().toString();
                    Context context = getApplicationContext();

                    int duration = Toast.LENGTH_SHORT;
                    String[] temp = search.getText().toString().split(" ");

                    searchString[0] = temp[0];
                    for (int i = 1; i < temp.length; i++) {
                        searchString[0] += "+" + temp[i];
                    }


//                Context context = getApplicationContext();
                    Toast.makeText(context, searchString[0], Toast.LENGTH_SHORT).show();
                    new AsyncTask<Void, Void, String>() {
                        String jsonStr1;

                        @Override
                        protected String doInBackground(Void... params) {
                            jsonStr1 = fetch("https://aggregator-scripts-azharullah.c9users.io/flipkart.php?query=" + searchString[0]);

                            return jsonStr1;
                        }

                        @Override
                        protected void onPostExecute(String result) {

                            Log.v("result", "size of the array is" + result);
                            Intent activityChangeIntent = new Intent(ProductContent.this, ProductContent.class);
                            activityChangeIntent.putExtra("message", result);
                            ProductContent.this.startActivity(activityChangeIntent);
                            return;
                        }


                    }.execute();

                }
                return false;
            }
        });

        new AsyncTask<Void, Void, ArrayList<ItemContent>>() {
            String jsonStr1;

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(ProductContent.this);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("Loading...Please wait...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setProgress(0);
                progressDialog.show();
            }

            @Override
            protected ArrayList<ItemContent> doInBackground(Void... params) {
                Log.v("before", "size of the array is" + array.size());
                Log.i("id",o1.getId());
                jsonStr1 = fetch("https://aggregator-scripts-azharullah.c9users.io/compare.php?id=" + o1.getId());

                jsonStr = jsonStr1.replace("<type 'str'>", "");
                jsonStr = jsonStr.replace("null", "");
                jsonStr = jsonStr.replace("u'", "\"");
                jsonStr = jsonStr.replace("'", "\"");
                jsonStr = jsonStr.replace("\\xc2\\xa0\\xc2\\xa0","");
                jsonStr = jsonStr.replace("u\"", "\"");

                Log.i("string",jsonStr);
                try {
                    if (jsonStr1.length() == 0) {
                        Log.v("String", "zero lengthed string");
                        onBackPressed();

                    } else
                        getDataFromJson(jsonStr);
//                        getDataFromJson(jsonStr1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.v("final", "size of the array is" + array.size());
                return array;
            }

            @Override
            protected void onPostExecute(ArrayList<ItemContent> result) {
                progressDialog.dismiss();
                Log.v("result", "size of the array is" + result.size());
                if(result.size() == 0)
                {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "There's some problem with the server... Please try again later...", Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
                return;
            }


        }.execute();
        final String[] searchString = new String[1];

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Object o = list.getItemAtPosition(position);

                ItemContent fullObject = (ItemContent) o;
                Context context = getApplicationContext();
                Toast.makeText(context, "You have chosen: " + " " + fullObject.getDescription(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fullObject.getId()));
                startActivity(i);

            }
        });
    }

    public void HomeIntent(View v) {
        Intent activityChangeIntent = new Intent(ProductContent.this, ProductContent.class);
        activityChangeIntent.putExtra("message","");
        ProductContent.this.startActivity(activityChangeIntent);
    }
    public void dealIntent(View v) {
        Intent activityChangeIntent = new Intent(ProductContent.this, Deals.class);
        ProductContent.this.startActivity(activityChangeIntent);
    }
    public void HelpIntent(View v) {
        Intent activityChangeIntent = new Intent(ProductContent.this, Help.class);
        ProductContent.this.startActivity(activityChangeIntent);
    }
    String fetch(String addr) {
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(addr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream is = con.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            String txt;
            txt = dis.readLine();
            sb.append(txt);
            while (txt != null) {
                Log.d("A", txt);
                txt = dis.readLine();
                sb.append(txt);
            }

        } catch (Exception e) {
            Log.e("Error connection", e.getMessage());
        }

        return sb.toString();
    }

    private void getDataFromJson(String FetchedJsonStr)
            throws JSONException {
        JSONArray forecastJson = new JSONArray(FetchedJsonStr);


        for (int i = 0; i < forecastJson.length(); i++) {

            JSONObject productObject = forecastJson.getJSONObject(i);
            ItemContent it = new ItemContent();
//            Log.i("xxx",productObject.getString("rating"));
            it.setTitle(productObject.getString("title"));
            it.setImgid(productObject.getString("imgurl"));
            it.setDescription(productObject.getString("server"));
            it.setMaxPrice(productObject.getString("price"));
            it.setId(productObject.getString("url"));
            array.add(it);
        }

        return;
    }



}
