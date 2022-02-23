package de.darkmodz.gastroappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Bestellen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellen);
        setTitle("Bestellt");

        Button b = findViewById(R.id.goback1);
        b.setOnClickListener(view -> finish());
    }
}