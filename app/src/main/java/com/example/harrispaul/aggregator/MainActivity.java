package com.example.harrispaul.aggregator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
/**
 * Created by Harrispaul on 3/14/2016.
 */

public class MainActivity  extends Activity  {

    ListView list;
    String jsonStr;
    ArrayList<ItemContent> array = new ArrayList<ItemContent>();
    ArrayList<ItemContent> array1 = new ArrayList<ItemContent>();
    ArrayList<ItemContent> array11 = new ArrayList<ItemContent>();
    ArrayList<ItemContent> array12 = new ArrayList<ItemContent>();
    ArrayList<Checkbox> toggle = new ArrayList<Checkbox>();
    EditText search;
    Button deals,sort,filter;
    ProgressBar progressBar;
    MyCustomBaseAdapter adapter;
    CheckBoxAdapter toggleAdapter;
    double min;
    double max;

    Integer pro,lowToHigh=0,highToLow=0;
    ArrayList<String> brands = new ArrayList<>();


//    FlyInMenu root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Button home =(Button) findViewById(R.id.home);


        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        jsonStr = bundle.getString("message");
        setContentView(R.layout.activity_main);
        deals = (Button) findViewById(R.id.deals);
        adapter = new MyCustomBaseAdapter(this, array);

        sort = (Button) findViewById(R.id.sort);
        filter = (Button) findViewById(R.id.filter);

        list = (ListView) findViewById(R.id.list_view_product);

        search = (EditText) findViewById(R.id.search_text);
        sort.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
//        final LinearLayout mainLayout=(LinearLayout)findViewById(R.id.homepng);
//        mainLayout.setVisibility(LinearLayout.VISIBLE);
//        this.root = (FlyInMenu) this.getLayoutInflater().inflate(R.layout.activity_main, null);
        list.setAdapter(adapter);
        if(jsonStr.length() != 0) {
            array.clear();
//            mainLayout.setVisibility(LinearLayout.GONE);
            adapter.notifyDataSetChanged();
            try {
                if (jsonStr.length() != 0) {
                    getDataFromJson(jsonStr);
                }
                else
                {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "There's some problem... Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
            sort.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);

        }
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    mainLayout.setVisibility(LinearLayout.GONE);
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
                    new AsyncTask<Void, Void, ArrayList<ItemContent>>() {
                        String jsonStr1;

                        private ProgressDialog progressDialog;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setCancelable(true);
                            progressDialog.setMessage("Loading...Please wait...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setProgress(0);
                            progressDialog.show();
                        }

                        @Override
                        protected ArrayList<ItemContent> doInBackground(Void... params) {
                            array.clear();
                            jsonStr1 = fetch("https://aggregator-scripts-azharullah.c9users.io/flipkart.php?query=" + searchString[0]);
                            try {
                                if (jsonStr1.length() == 0) {
                                    Log.v("String", "zero lengthed string");
                                    Context context = getApplicationContext();
                                    Toast.makeText(context, "There's some problem... Check your internet connection", Toast.LENGTH_LONG).show();
                                } else
                                    getDataFromJson(jsonStr1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return array;
                        }

                        @Override
                        protected void onPostExecute(ArrayList<ItemContent> result) {
                            progressDialog.dismiss();
                            adapter.notifyDataSetChanged();
                            sort.setVisibility(View.VISIBLE);
                            filter.setVisibility(View.VISIBLE);
                            return;
                        }


                    }.execute();

                }
                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Object o = list.getItemAtPosition(position);
                Intent ourIntent;
                ItemContent fullObject = (ItemContent) o;
                Context context = getApplicationContext();
                Toast.makeText(context, "You have chosen: " + " " + fullObject.getTitle(), Toast.LENGTH_SHORT).show();
                ourIntent = new Intent(MainActivity.this, ProductContent.class);

                ourIntent.putExtra("product", fullObject);
                startActivity(ourIntent);

            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //Open popup window
                showPopup(MainActivity.this);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //Open popup window
                filterPopUp(MainActivity.this);
            }
        });
    }

    public void dealIntent(View v){
        Intent activityChangeIntent = new Intent(MainActivity.this, Deals.class);
        MainActivity.this.startActivity(activityChangeIntent);
    }
    public void HomeIntent(View v){
//        Intent activityChangeIntent = new Intent(MainActivity.this, MainActivity.class);
//        MainActivity.this.startActivity(activityChangeIntent);
//        Log.i("same activity","");
        return;
    }
    public void HelpIntent(View v){
        Intent activityChangeIntent = new Intent(MainActivity.this, Help.class);
        MainActivity.this.startActivity(activityChangeIntent);
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

    private void getDataFromJson(String forecastJsonStr)
            throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray("productInfoList");

        min = 0.0;
        max = 0.0;
        brands.clear();
        toggle.clear();
        for (int i = 0; i < weatherArray.length(); i++) {

            JSONObject productObject = weatherArray.getJSONObject(i);
            ItemContent it = new ItemContent();
            Checkbox cb=new Checkbox();
            it.setTitle(productObject.getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("title"));
            it.setImgid(productObject.getJSONObject("productBaseInfo").getJSONObject("productAttributes").getJSONObject("imageUrls").getString("200x200"));
            it.setDescription(productObject.getJSONObject("productBaseInfo").getJSONObject("productIdentifier").getJSONObject("categoryPaths").getJSONArray("categoryPath").getJSONArray(0).getJSONObject(0).getString("title"));
//            it.setMaxPrice(productObject.getJSONObject("productBaseInfo").getJSONObject("productAttributes").getJSONObject("maximumRetailPrice").getString("amount"));
            String brand=productObject.getJSONObject("productBaseInfo").getJSONObject("productAttributes").getString("productBrand");
            it.setBrand(brand);
            String sellingPrice = productObject.getJSONObject("productBaseInfo").getJSONObject("productAttributes").getJSONObject("sellingPrice").getString("amount");
            it.setSellingPrice(sellingPrice);
            int k = 0;
            for(int j=0;j<brands.size();j++){
                k=0;
                if(Objects.equals(brand,brands.get(j))){
                    k=1;
                    break;
                }
            }
            if(k == 0){
                brands.add(brand);
                cb.setName(brand);
                cb.setToggle(1);
                toggle.add(cb);
            }
            if(min == 0){
                min = Double.parseDouble(sellingPrice);
            }
            else if(min > Double.parseDouble(sellingPrice)){
                min = Double.parseDouble(sellingPrice);
            }
            if(max == 0){
                max=Double.parseDouble(sellingPrice);
            }
            else if(max < Double.parseDouble(sellingPrice)){
                max = Double.parseDouble(sellingPrice);
            }
            it.setId(productObject.getJSONObject("productBaseInfo").getJSONObject("productIdentifier").getString("productId"));
            array.add(it);
            array1.add(it);

        }
        pro=(int)max;
        return;
    }
    public static Comparator<ItemContent> StringAscComparator = new Comparator<ItemContent>() {

        public int compare(ItemContent app1, ItemContent app2) {

            float price1 = Float.parseFloat(app1.getSellingPrice());
            float price2 = Float.parseFloat(app2.getSellingPrice());
            if(price1 > price2){
                return 1;
            } else {
                return -1;
            }
        }
    };
    public static Comparator<ItemContent> StringDescComparator = new Comparator<ItemContent>() {

        public int compare(ItemContent app1, ItemContent app2) {

            float price1 = Float.parseFloat(app1.getSellingPrice());
            float price2 = Float.parseFloat(app2.getSellingPrice());
            if (price2 > price1) {
                return 1;
            } else {
                return -1;
            }
        }
    };


    private void showPopup(final Activity context) {
        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup, viewGroup);
        final RadioButton lth,htl;


        lth = (RadioButton) layout.findViewById(R.id.lth);
        htl = (RadioButton) layout.findViewById(R.id.htl);

        if(lowToHigh==0){
            lth.setChecked(false);
        }
        else{
            lth.setChecked(true);
        }
        if(highToLow==0){
            htl.setChecked(false);
        }
        else{
            htl.setChecked(true);
        }
        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setHeight(400);
        popup.setWidth(600);
        popup.setFocusable(true);


        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, 80, 400);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        lth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Collections.sort(array, StringAscComparator);
                lowToHigh=1;
                highToLow=0;
//                popup.dismiss();
             }
        });
        htl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Collections.sort(array, StringDescComparator);
                lowToHigh=0;
                highToLow=1;
//                popup.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();

                popup.dismiss();
            }
        });
    }
    private void filterPopUp(final Activity context) {
        // Inflate the popup_layout.xml
        final TextView minimum,maximum,now;
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.filterPop);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.filter_popup, viewGroup);
        if(brands.size()!=0) {
            toggleAdapter = new CheckBoxAdapter(context,toggle);
            ListView listView = (ListView) layout.findViewById(R.id.brands);
            listView.setAdapter(toggleAdapter);
            minimum = (TextView) layout.findViewById(R.id.min);
            now = (TextView) layout.findViewById(R.id.now);
            maximum = (TextView) layout.findViewById(R.id.max);
            minimum.setText(Integer.toString((int)min));
            maximum.setText(Integer.toString((int)max));
            now.setText(Integer.toString(pro));

            SeekBar priceFilter = (SeekBar) layout.findViewById(R.id.seekBar);

            priceFilter.setMax((int)(max-min));
            priceFilter.setProgress(50);
            priceFilter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    pro=(int)min + progress;
                    now.setText(Integer.toString(pro));
                }

                public void onStartTrackingTouch(SeekBar seekBar) {}

                public void onStopTrackingTouch(SeekBar seekBar) {
                    //we can use the progress value of pro as anywhere

                    final Toast toast =  Toast.makeText(getBaseContext(), String.valueOf(pro),Toast.LENGTH_SHORT);
                    toast.show();
                    Integer i=0;
                    ArrayList<ItemContent> array2 = new ArrayList<ItemContent>();
                    array2 = array;
//                    array11 = array;
                    array.clear();

                    Log.i("String","added as");

                    for(i=0;i<array2.size();i++){
                        ItemContent o = array1.get(i);
                        Log.i("String","addeded...");
                        if((int)Double.parseDouble(o.getSellingPrice()) < pro){
                            Log.i("added " + i,o.getBrand());
                            array.add(o);
                        }
                    }
//                    adapter.notifyDataSetChanged();

                }
            });
        }



        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);;
        popup.setHeight(900);
        popup.setWidth(600);
        popup.setFocusable(true);


        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, 80, 200);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                popup.dismiss();
            }
        });

    }
    public void checkboxToggled(View v){
        CheckBox checkboxBrand=(CheckBox)v.findViewById(R.id.checkbox);
        if(checkboxBrand.isChecked()){
            for(int i=0;i<array1.size();i++){
                if(array1.get(i).getBrand().equals(checkboxBrand.getText())){
                    array.add(array1.get(i));
                    if(lowToHigh == 1){
                        Collections.sort(array, StringAscComparator);
                    }
                    if(highToLow ==0){
                        Collections.sort(array, StringDescComparator);
                    }
                }
            }
            for(int i=0;i<toggle.size();i++){
                if(toggle.get(i).getName().equals(checkboxBrand.getText())){
                    toggle.get(i).setToggle(1);
                }
            }
        }
        else{
            for(int i=0;i<array.size();i++){
                if(array.get(i).getBrand().equals(checkboxBrand.getText())){
                    array.remove(i);
                    if(lowToHigh == 1){
                        Collections.sort(array, StringAscComparator);
                    }
                    if(highToLow ==0){
                        Collections.sort(array, StringDescComparator);
                    }
                }
            }
            for(int i=0;i<toggle.size();i++){
                if(toggle.get(i).getName().equals(checkboxBrand.getText())){
                    toggle.get(i).setToggle(0);
                }
            }
        }

    }
}