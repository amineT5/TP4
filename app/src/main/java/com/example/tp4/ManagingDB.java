package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagingDB extends AppCompatActivity {

    private Button b;
    private ListView lv;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managing_db);

        b = findViewById(R.id.button);
        lv = findViewById(R.id.listView);
        dataBase = new DataBase(this);
        viewData();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagingDB.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] items = {"Modifier", "Supprimer"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ManagingDB.this);
                builder.setTitle("Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            showUpdate(ManagingDB.this, lv, position);
                        } else if (which == 1) {
                            delete(lv, position);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void showUpdate(Activity ac, ListView lv, int p) {
        Dialog dialog = new Dialog(ac);
        dialog.setContentView(R.layout.update_db);
        dialog.setTitle("Update");
        final EditText name = dialog.findViewById(R.id.editText2);
        final EditText mail = dialog.findViewById(R.id.editText3);
        final EditText phone = dialog.findViewById(R.id.editText4);
        Button bt = dialog.findViewById(R.id.button3);
        final String[] chaine = lv.getAdapter().getItem(p).toString().split(" ");
        name.setText(chaine[1]);
        mail.setText(chaine[2]);
        phone.setText(chaine[3]);
        int width = (int) (ac.getResources().getDisplayMetrics().widthPixels * 0.9);
        int height = (int) (ac.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(chaine[0]);
                dataBase.update(name.getText().toString(), mail.getText().toString(), phone.getText().toString(), i);
                Toast.makeText(ManagingDB.this, "Mise à jour réussie", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                viewData();
            }
        });
    }

    private void delete(ListView lv, int p) {
        String[] chaine = lv.getAdapter().getItem(p).toString().split(" ");
        int i = Integer.parseInt(chaine[0]);
        dataBase.delete(i);
        Toast.makeText(this, "Suppression réussie", Toast.LENGTH_SHORT).show();
        viewData();
    }

    private void viewData() {
        Cursor c = dataBase.getAllData();
        ArrayList<String> list = new ArrayList<>();

        if (c.getCount() == 0) {
            Toast.makeText(ManagingDB.this, "La base est vide", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                list.add(c.getString(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3));
            }
        }

        ListAdapter listAdapter = new ArrayAdapter<>(ManagingDB.this,
                android.R.layout.simple_list_item_1, list);
        lv.setAdapter(listAdapter);
    }
}