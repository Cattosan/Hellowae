package com.example.hewooworlddd2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivitiezz";
    private Button btn;
    private EditText areaus;
    private EditText areapazz;
    private String users = "admin";
    private String pazz = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Berhasil", "In already");
        setContentView(R.layout.activity_main);
        areaus = findViewById(R.id.username);
        areapazz = findViewById(R.id.password);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validData();
            }
        });

        Log.d(TAG, "onCreate: Message nya apa bebas sih, ini updatenya");
        //Log.i(TAG, "onCreate: ");
    }

    public void validData(){
        if(areaus.getText().toString().equals(users) && areapazz.getText().toString().equals(pazz)){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Username atau Pass anda salah", Toast.LENGTH_LONG).show();
        }
    }
}