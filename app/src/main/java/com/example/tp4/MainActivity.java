package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button b1, b2;
    private EditText nom, mail, phone;
    com.example.databasesql.DataBase dbb;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        nom = findViewById(R.id.nom);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.Phone);
        dbb = new com.example.databasesql.DataBase(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nom.getText().toString().equalsIgnoreCase("") &&
                        !mail.getText().toString().equalsIgnoreCase("") &&
                        !phone.getText().toString().equalsIgnoreCase("")) {
                    boolean inserted = dbb.insertData(nom.getText().toString(), mail.getText().toString(), phone.getText().toString());
                    if (inserted)
                        Toast.makeText(MainActivity.this, "Insertion réussie", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Échec de l'insertion", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void OnClick() {
                onClick(null);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.databasesql.ManagingDB.class);
                startActivity(intent);
            }
        });
    }
}