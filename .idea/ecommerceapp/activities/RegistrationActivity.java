package space.ali.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import space.ali.ecommerceapp.R;

public class RegistrationActivity extends AppCompatActivity {

    //Initialize variable
    ImageView ivFacebook,ivInstagram;

    EditText name,email,password;
    private FirebaseAuth auth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        //Логин мен парольсыз кіру
        if(auth.getCurrentUser() != null){

           startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
           finish();
        }


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

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



        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if (isFirstTime){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent = new Intent(RegistrationActivity.this,OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

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

    public void signup(View view) {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Атыңызды енгізіңіз!", Toast.LENGTH_SHORT).show();
            return;
        }

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

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Тіркелу сәтті өтті", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Қате: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signin(View view) {

        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }
}