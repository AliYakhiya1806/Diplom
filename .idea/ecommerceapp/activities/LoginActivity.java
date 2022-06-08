package space.ali.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import space.ali.ecommerceapp.R;

public class LoginActivity extends AppCompatActivity {

    //Initialize variable
    ImageView ivFacebook,ivInstagram;

    EditText email,password;
    private FirebaseAuth auth;
//    private Button Btn;
//    private TextView AdminLink, NotAdminLink;
//
//    private String parentDbName ="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

//        Btn = (Button) findViewById(R.id.button);
//
//        AdminLink = (TextView)findViewById(R.id.admin_panel_link);
//        NotAdminLink = (TextView)findViewById(R.id.not_admin_panel_link);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

//        AdminLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AdminLink.setVisibility(View.INVISIBLE);
//                NotAdminLink.setVisibility(View.VISIBLE);
//                Btn.setText("Админ үшін кіру");
//                parentDbName ="Admins";
//            }
//        });
//
//        NotAdminLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AdminLink.setVisibility(View.VISIBLE);
//                NotAdminLink.setVisibility(View.INVISIBLE);
//                Btn.setText("Кіру");
//                parentDbName ="Users";
//            }
//        });

        //Assign variable
        ivFacebook = findViewById(R.id.iv_facebook);
        ivInstagram = findViewById(R.id.iv_instagram);

        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize link and package
                String sAppLink = "https://ru-ru.facebook.com/";
                String sPackage = "com.instagram.katana";
                String sWebLink = "https://ru-ru.facebook.com/";
                //Call method
                openLink(sAppLink,sPackage,sWebLink);

            }
        });

        ivInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize link and package
                String sAppLink = "https://www.instagram.com/";
                String sPackage = "https://www.instagram.com/";
                //Call method
                openLink(sAppLink,sPackage,sAppLink);
            }
        });
    }

    private void openLink(String sAppLink, String sPackage, String sWebLink) {
        //Use try catch
        try {
            //When application is installed
            //Initialize uri
            Uri uri = Uri.parse(sAppLink);
            //Initialize intant
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Set data
            intent.setData(uri);
            //Set package
            intent.setPackage(sPackage);
            //Set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Start activity
            startActivity(intent);
        }catch (ActivityNotFoundException activityNotFoundException){
            //Open link in browser
            //Initialize uri
            Uri uri = Uri.parse(sWebLink);
            //Initialize intent
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Set data
            intent.setData(uri);
            //Set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Start Activity
            startActivity(intent);
        }
    }

    public void signIn(View view) {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Электрондық поштаны енгізіңіз!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Парольді енгізіңіз!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6) {
            Toast.makeText(this, "Парольдің ұзындығы 6 саннан артық болуы керек", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Сәтті кірілді", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Қате: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void signUp(View view) {

        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}