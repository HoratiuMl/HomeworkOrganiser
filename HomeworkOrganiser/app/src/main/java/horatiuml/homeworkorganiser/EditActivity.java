package horatiuml.homeworkorganiser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    // UI references.
    private EditText mTitle;
    private EditText mDescription;
    private EditText mDeadline;
    private EditText mGrade;

    private String userId;
    private String homeworkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mTitle = (EditText)findViewById(R.id.editTextTitle);
        mDescription = (EditText)findViewById(R.id.editTextDescription);
        mDeadline = (EditText)findViewById(R.id.editTextDeadline);
        mGrade = (EditText)findViewById(R.id.editTextGrade);

        userId = (String)getIntent().getSerializableExtra("USERID");
        homeworkId = (String)getIntent().getSerializableExtra("HOMEWORKID");

        Button mSave = (Button) findViewById(R.id.buttonSave);
        Button mCancel = (Button) findViewById(R.id.buttonCancel);
        Button mDelete = (Button) findViewById(R.id.buttonDelete);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHomework();
                finish();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteHomework(); finish();
            }
        });

        loadHomework();
    }

    public void loadHomework() {
        String REQUEST_URL = "http://10.0.2.2:5000/api/homeworks/" + homeworkId;
        Map<String,String> params = new HashMap<String,String>();
        params.put("get", "one");

        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.GET, REQUEST_URL, params,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = response.getString("title");
                            String description = response.getString("description");
                            String deadline = response.getString("deadline");
                            String grade = response.getString("grade");

                            mTitle.setText(title);
                            mDescription.setText(description);
                            mDeadline.setText(deadline);
                            mGrade.setText(grade);
                        } catch (JSONException e) {
                            // TODO: Handle exception
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error response
            }
        });

        jsonObjectRequest.setTag("GetTag");

        Volley.newRequestQueue(EditActivity.this).add(jsonObjectRequest);
    }

    public void saveHomework() {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();
        String deadline = mDeadline.getText().toString();
        String grade = mGrade.getText().toString();

        String REQUEST_URL = "http://10.0.2.2:5000/api/homeworks/" + homeworkId;
        Map<String,String> params = new HashMap<String,String>();
        params.put("title", title);
        params.put("description", description);
        params.put("deadline", deadline);
        params.put("grade", grade);

        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.PUT, REQUEST_URL, params,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO: Inform the user whether the request was made successfully or not
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error response
            }
        });

        jsonObjectRequest.setTag("EditTag");

        Volley.newRequestQueue(EditActivity.this).add(jsonObjectRequest);
    }

    public void deleteHomework() {
        String REQUEST_URL = "http://10.0.2.2:5000/api/homeworks/" + homeworkId;
        Map<String,String> params = new HashMap<String,String>();
        params.put("delete", "this");

        CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.DELETE, REQUEST_URL, params,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO: Inform the user whether the request was made successfully or not
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error response
            }
        });

        jsonObjectRequest.setTag("DeleteTag");

        Volley.newRequestQueue(EditActivity.this).add(jsonObjectRequest);
    }
}
