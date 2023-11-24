package ninh.main.mybarcodescanner.SignInUp;

import static ninh.main.mybarcodescanner.R.id.btnSignUpSU;
import static ninh.main.mybarcodescanner.R.id.tvUsernameSU;

import android.annotation.SuppressLint;
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
import androidx.camera.core.processing.SurfaceProcessorNode;

import ninh.main.mybarcodescanner.R;

public class SignUp extends AppCompatActivity {
    EditText tvUsernameSU, tvPasswordSU;
    Button btnSignUp , btnSignIn;
    DBSignIn DB;
    // aaaaaaa
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        tvUsernameSU = findViewById(R.id.tvUsernameSU);
        tvPasswordSU = findViewById(R.id.tvPasswordSU);
        btnSignUp = findViewById(btnSignUpSU);
        btnSignIn = findViewById(R.id.btnSignInSU);
        DB = new DBSignIn(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = tvUsernameSU.getText().toString();
                String pass = tvPasswordSU.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(SignUp.this, "Fill full the information", Toast.LENGTH_SHORT).show();
                }
                else {
                        boolean checkuser = DB.checkUser(user);
                        if(checkuser == false){
                            boolean insert = DB.insert(user,pass);
                            if(insert == true){
                                Toast.makeText(SignUp.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignUp.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUp.this, "Account Already Existed", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });

    }
}




