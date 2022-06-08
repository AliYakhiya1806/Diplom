package space.ali.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import space.ali.ecommerceapp.R;
import space.ali.ecommerceapp.ui.home.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    Fragment homeFragment;
    FirebaseAuth auth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment homeFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout){

            auth.signOut();
            startActivity(new Intent(HomeActivity.this,RegistrationActivity.class));
            finish();

        }
//        else if (id == R.id.android){
//            Toast.makeText(this, "Бұл Android опциясының элементі", Toast.LENGTH_SHORT).show();
//        }
        else if (id == R.id.profile){
            Toast.makeText(this, "Бұл профиль элементі", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
        }
//        else if (id == R.id.download){
//            Toast.makeText(this, "Бұл жүктеу элементі", Toast.LENGTH_SHORT).show();
//        }
//        else if (id == R.id.setting){
//            Toast.makeText(this, "Бұл настройка элементі", Toast.LENGTH_SHORT).show();
//        }
        else if (id == R.id.scanner){
            startActivity(new Intent(HomeActivity.this,ScannerActivity.class));
        }
        else if (id == R.id.menu_my_cart){
            startActivity(new Intent(HomeActivity.this,CartActivity.class));
        }
        
        return true;
    }
}