package horatiuml.homeworkorganiser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView mListViewHomeworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewHomeworks = (ListView)findViewById(R.id.listViewHomeworks);

        Button mNew = (Button) findViewById(R.id.buttonNew);
        Button mRefresh = (Button) findViewById(R.id.buttonRefresh);

        mNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(MainActivity.this, AddActivity.class);
                        MainActivity.this.startActivity(myIntent);}
                });
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHomeworks();
            }
        });

        loadHomeworks();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        loadHomeworks();
    }

    private void loadHomeworks() {
        // Create a List from String Array elements
        final List<HomeworkListItem> fruits_list = new ArrayList<HomeworkListItem>();

        // Create an ArrayAdapter from List
        final ArrayAdapter<HomeworkListItem> arrayAdapter = new ArrayAdapter<HomeworkListItem>
                (this, android.R.layout.simple_list_item_1, fruits_list);

        // DataBind ListView with items from ArrayAdapter
        mListViewHomeworks.setAdapter(arrayAdapter);

        String REQUEST_URL = "http://10.0.2.2:5000/api/homeworks";
        Map<String,String> params = new HashMap<String,String>();
        params.put("get", "all");

        CustomRequestArray jsonObjectRequest = new CustomRequestArray(Request.Method.GET, REQUEST_URL, params,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonobject = response.getJSONObject(i);

                                String id = jsonobject.getString("id");
                                String title = jsonobject.getString("title");

                                HomeworkListItem hli = new HomeworkListItem(id, title);

                                fruits_list.add(hli);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error response
            }
        });

        jsonObjectRequest.setTag("GetTag");

        Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);

        mListViewHomeworks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                HomeworkListItem hli = arrayAdapter.getItem(position);
                // TODO: Open the selected item in the edit activity
                Intent myIntent = new Intent(MainActivity.this, EditActivity.class);
                myIntent.putExtra("HOMEWORKID", hli.getId());
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
