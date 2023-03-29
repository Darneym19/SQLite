package com.mobie.sqlite;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText datoCel, datoNom, datoTel;
    public Button guardar, consultar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datoCel = (EditText)findViewById(R.id.datoCel);
        datoNom = (EditText)findViewById(R.id.datoNom);
        datoTel = (EditText)findViewById(R.id.datoTel);
        guardar = findViewById(R.id.registrar);
        consultar = findViewById(R.id.consultar);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Consultar(view);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar(view);
            }
        });
    }
    public void Registrar(View view){
        AdminBD admin = new AdminBD(this, "BaseDatos",null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String cedula = datoCel.getText().toString();
        String nombre = datoNom.getText().toString();
        String telefono = datoTel.getText().toString();
        if (!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty())
        {
            ContentValues Registro = new ContentValues();
            Registro.put("cedula", cedula);
            Registro.put("nombre", nombre);
            Registro.put("telefono", telefono);
            BaseDatos.insert("usuario", null, Registro);
            BaseDatos.close();
            datoCel.setText("");
            datoNom.setText("");
            datoTel.setText("");
            Toast.makeText(this,"Usuario Guardado", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Error, datos incorrectos", Toast.LENGTH_LONG).show();
        }
    }
    public void Consultar(View view){
        AdminBD admin = new AdminBD(this,"BaseDatos",null,1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String cedula1 = datoCel.getText().toString();
        Log.i("Depuracion","Entré a cedula"+cedula1.isEmpty());
        if (!cedula1.isEmpty())
        {
            Cursor fila = BaseDatos.rawQuery("select nombre, telefono from Usuario where cedula=" +cedula1,null);

            if (fila.moveToFirst())
            {
                Log.i("Depuracion","Nombre "+fila.getString(0));
                datoNom.setText(fila.getString(0));
                datoTel.setText(fila.getString(1));
                BaseDatos.close();
            }
            else
            {
                Toast.makeText(this,"No se encontro usuario", Toast.LENGTH_LONG).show();
            }
        }
    }
}