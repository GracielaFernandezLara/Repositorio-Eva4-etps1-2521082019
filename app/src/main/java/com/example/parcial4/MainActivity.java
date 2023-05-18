package com.example.parcial4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private EditText editTextName;
    private Button buttonSave;
    private ListView listView;
    private List<Registro> registros;
    private ArrayAdapter<Registro> adapter;
    private MiDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MiDBHelper(this);

        editTextName = findViewById(R.id.editTextName);
        buttonSave = findViewById(R.id.buttonSave);
        listView = findViewById(R.id.listView);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextName.getText().toString().trim();
                guardarRegistro(nombre);
                cargarRegistros();
                editTextName.setText("");
            }
        });

        listView.setOnItemClickListener(this);
        cargarRegistros();
    }

    private void guardarRegistro(String nombre) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        db.insert("mi_tabla", null, values);
        db.close();
        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
    }

    private void cargarRegistros() {
        registros = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("mi_tabla", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            Registro registro = new Registro(id, nombre);
            registros.add(registro);
        }

        cursor.close();
        db.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registros);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Registro registro = registros.get(position);
        borrarRegistro(registro);
        cargarRegistros();
    }

    private void borrarRegistro(Registro registro) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("mi_tabla", "id=?", new String[]{String.valueOf(registro.getId())});
        db.close();
        Toast.makeText(this, "Registro borrado", Toast.LENGTH_SHORT).show();
    }
}
