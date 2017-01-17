package horatiuml.homeworkorganiser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    // UI references.
    private EditText mTitle;
    private EditText mDescription;
    private EditText mDeadline;
    private EditText mGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mTitle = (EditText)findViewById(R.id.editTextTitle);
        mDescription = (EditText)findViewById(R.id.editTextDescription);
        mDeadline = (EditText)findViewById(R.id.editTextDeadline);
        mGrade = (EditText)findViewById(R.id.editTextGrade);

        Button mAdd = (Button) findViewById(R.id.addButton);
        Button mCancel = (Button) findViewById(R.id.cancelButton);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHomework();
                finish();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addHomework() {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();
        String deadline = mDeadline.getText().toString();
        String grade = mGrade.getText().toString();

        String REQUEST_URL = "http://10.0.2.2:5000/api/homeworks";
        Map<String,String> params = new HashMap<String,String>();
        params.put("title", title);
        params.put("description", description);
        params.put("deadline", deadline);
        params.put("grade", grade);

        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, REQUEST_URL, params,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")){
                                // TODO: Inform the user that the request was sucessful
                            } else {
                                /// TODO: Inform the user that the request failed (and why)
                            }
                        } catch (JSONException e) {
                            // TODO: Handle exception
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error response
            }
        });

        jsonObjectRequest.setTag("AddTag");

        Volley.newRequestQueue(AddActivity.this).add(jsonObjectRequest);
    }
}
