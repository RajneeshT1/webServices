package com.acadview.webservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.networkutil.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ImageView githubImage;
    TextView githubName,githubBio;
    EditText user;
    Button go;
    String BASE_URL = "https://api.github.com/users/";
    String data = null;
    String gitName,Bio,Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        githubImage = findViewById(R.id.githubImage);
        githubName = findViewById(R.id.githubName);
        githubBio = findViewById(R.id.githubBio);
        go = findViewById(R.id.go);
        user = findViewById(R.id.user);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadData().execute();
            }
        });

    }

    class LoadData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

           String userName = user.getText().toString();

           if(userName == null){
               return null;
           }
           String url = BASE_URL+userName;
            data = NetworkUtil.makeServiceCall(url);
            try {
                JSONObject jsonObject = new JSONObject(data);
                gitName = jsonObject.getString("name");
                Bio = jsonObject.getString("bio");
                Image = jsonObject.getString("avatar_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(gitName.equals("null")){
                githubName.setText("null");
            }else{
                githubName.setText(gitName);
            }
            if(Bio.equals("null")){
                githubBio.setText("null");
            }else{
                githubBio.setText(Bio);
            }
            Glide.with(MainActivity.this).load(Image).into(githubImage);
        }
    }
}
