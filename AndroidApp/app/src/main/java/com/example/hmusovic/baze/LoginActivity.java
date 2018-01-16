package com.example.hmusovic.baze;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SharedPreferences sharedPreferences;

    Button buttonSignIn;
    EditText editUser, editPw;
    TextView loginLabela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = DatabaseClient.getInstance(this).getDb();

        // ako je neko vec logovan da ga redirecta
        sharedPreferences  = getSharedPreferences("local1", Context.MODE_PRIVATE);
        int korisnikID = sharedPreferences.getInt("loginID", -1);
        if (korisnikID != -1) {
            Cursor c = db.rawQuery("SELECT ulogaId FROM korisnik WHERE _id = ?", new String[] {String.valueOf(korisnikID)});
            assert c != null;
            c.moveToLast();

            int ulogaId = c.getInt(0);
            c.close();

            // profesor
            if(ulogaId == 1) {
                Intent i = new Intent(this, MainActivityProfesor.class);
                startActivity(i);
                finish();
            }
            // student
            else if (ulogaId == 2) {
                Intent i = new Intent(this, MainActivityStudent.class);
                startActivity(i);
                finish();
            }
            // sluzba
            else {
                Intent i = new Intent(this, MainActivitySluzba.class);
                startActivity(i);
                finish();
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        editUser = (EditText) findViewById(R.id.editUsername);
        editPw = (EditText) findViewById(R.id.editPassword);
        loginLabela = (TextView) findViewById(R.id.textLoginLabela);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard(v);
                loginLabela.setVisibility(View.INVISIBLE);

                Integer korisnikId;
                String username = editUser.getText().toString();
                String password = editPw.getText().toString();

                Cursor c = db.rawQuery("SELECT _id FROM korisnik WHERE username = ? AND password = ?", new String[] {username, password});
                if(c.getCount() == 0) {
                    // nije pogodio
                    loginLabela.setVisibility(View.VISIBLE);
                    loginLabela.setText("POGRESAN USERNAME I/ILI PASSWORD");

                }
                else {
                    // postoji korisnik
                    c.moveToFirst();
                    korisnikId = c.getInt(c.getColumnIndex("_id"));

                    sharedPreferences.edit().putInt("loginID", korisnikId).apply();

                    Cursor c1 = db.rawQuery("SELECT ulogaId FROM korisnik WHERE _id = ?", new String[] {String.valueOf(korisnikId)});
                    assert c1 != null;
                    c1.moveToLast();

                    int ulogaId = c1.getInt(0);
                    c1.close();

                    // profesor
                    if(ulogaId == 1) {
                        Intent i = new Intent(LoginActivity.this, MainActivityProfesor.class);
                        startActivity(i);
                        finish();
                    }
                    // student
                    else if (ulogaId == 2) {
                        Intent i = new Intent(LoginActivity.this, MainActivityStudent.class);
                        startActivity(i);
                        finish();
                    }
                    // sluzba
                    else {
                        Intent i = new Intent(LoginActivity.this, MainActivitySluzba.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
