package com.example.campusstage2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.campusstage2.model.Users;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Button registerButton = findViewById(R.id.register_button);
        EditText username = findViewById(R.id.register_username_input);
        EditText password = findViewById(R.id.register_password_input);
        EditText email = findViewById(R.id.register_email_input);
        EditText phone = findViewById(R.id.register_phone_input);
        EditText name = findViewById(R.id.register_name_input);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please enter username!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(phone.getText().toString().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please enter phone number!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(email.getText().toString().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please enter email!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(name.getText().toString().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please enter your name!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().toString().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please enter your password!", Toast.LENGTH_LONG).show();
                    return;
                }
                Users users = new Users(view.getContext());
                SQLiteDatabase db = users.dbHelper.getReadableDatabase();
                String query = "SELECT " +
                        "(SELECT COUNT(*) FROM users WHERE username = ?) AS usernameExists, " +
                        "(SELECT COUNT(*) FROM users WHERE email = ?) AS emailExists, " +
                        "(SELECT COUNT(*) FROM users WHERE phone = ?) AS phoneExists";
                Cursor cursor = db.rawQuery(query, new String[]{
                        username.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString()
                });
                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") int usernameExists =
                            cursor.getInt(cursor.getColumnIndex("usernameExists")) ;
                    @SuppressLint("Range") int emailExists =
                            cursor.getInt(cursor.getColumnIndex("emailExists"));
                    @SuppressLint("Range") int phoneExists =
                            cursor.getInt(cursor.getColumnIndex("phoneExists"));

                    if (usernameExists > 0) {
                        Toast.makeText(view.getContext(), "" +
                                "Username already exists", Toast.LENGTH_LONG).show();
                    } else if (emailExists > 0) {
                        Toast.makeText(view.getContext(),
                                "Email already exists", Toast.LENGTH_LONG).show();
                    } else if (phoneExists > 0) {
                        Toast.makeText(view.getContext(),
                                "Phone number already exists", Toast.LENGTH_LONG).show();
                    } else {
                        users.insertUser(username.getText().toString(),
                                password.getText().toString(),
                                phone.getText().toString(),
                                email.getText().toString(),
                                name.getText().toString()
                        );
                        Toast.makeText(view.getContext(),
                                "Register success!!!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        view.getContext().startActivity(intent);
                    }
                }
            }
        });


        TextView loginLink = findViewById(R.id.back_to_login);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}