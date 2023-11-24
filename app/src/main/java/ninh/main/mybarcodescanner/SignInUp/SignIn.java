package ninh.main.mybarcodescanner.SignInUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.ui.home.MainActivity;

public class SignIn extends AppCompatActivity {
    TextView tvWelcome, tvSignInToContinue;
    EditText tvUsername, tvPassword;
    Button btnSignIn, btnSignUp;
    DBSignIn db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        db = new DBSignIn(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = tvUsername.getText().toString();
                String pass = tvPassword.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(SignIn.this, "Not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuserpass = db.checkUserPassword(user, pass);
                    if (checkuserpass) {
                        Toast.makeText(SignIn.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                        Intent intent;
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignIn.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signUp);
            }
        });
    }
}


