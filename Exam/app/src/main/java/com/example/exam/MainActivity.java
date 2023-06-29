package com.example.exam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exam.databinding.ActivityMainBinding;

import java.io.IOException;

import monhoc.HoaFragment;
import monhoc.Home1Fragment;
import monhoc.LyFragment;
import monhoc.SinhFragment;
import monhoc.TienganhFragment;
import monhoc.ToanFragment;
import question.DBHelper;
import question.SearchFragment;
import score.ScoreFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.home, R.id.toan, R.id.sinh, R.id.hoa, R.id.tienganh, R.id.ly, R.id.diem, R.id.search)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new Home1Fragment()).commit();
        drawer.closeDrawer(GravityCompat.START);

        DBHelper db = new DBHelper(this);
//        try {
//            db.deleteDataBase();
//            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
//        }

        try {
            db.createDataBase();
            //Toast.makeText(this, "Coppy thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Thêm đoạn mã sau để liên kết HomeFragment với menu item Home.
        MenuItem homeMenuItem = navigationView.getMenu().findItem(R.id.home);
        homeMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new Home1Fragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết ToanFragment với menu item Toán.
        MenuItem toanMenuItem = navigationView.getMenu().findItem(R.id.toan);
        toanMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new ToanFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết SinhFragment với menu item sinh.
        MenuItem sinhMenuItem = navigationView.getMenu().findItem(R.id.sinh);
        sinhMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new SinhFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết HoaFragment với menu item hoa.
        MenuItem hoaMenuItem = navigationView.getMenu().findItem(R.id.hoa);
        hoaMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new HoaFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết TienganhFragment với menu item tienganh.
        MenuItem tienganhMenuItem = navigationView.getMenu().findItem(R.id.tienganh);
        tienganhMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new TienganhFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết LyFragment với menu item ly.
        MenuItem lyMenuItem = navigationView.getMenu().findItem(R.id.ly);
        lyMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new LyFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết SearchFragment với menu item search.
        MenuItem searchMenuItem = navigationView.getMenu().findItem(R.id.search);
        searchMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new SearchFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết ScoreFragment với menu item Diem.
        MenuItem diemMenuItem = navigationView.getMenu().findItem(R.id.diem);
        diemMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new ScoreFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để liên kết EmailFragment với menu item email.
        MenuItem emailMenuItem = navigationView.getMenu().findItem(R.id.email);
        emailMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openEmailApp();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // Thêm đoạn mã sau để xử lý khi nhấn vào "Message: 0363334505".
        MenuItem messageItem = navigationView.getMenu().findItem(R.id.message);
        messageItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Mở ứng dụng nhắn tin với số điện thoại gửi đến là 0363334505.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + "0363334505"));
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    // Function to open the email application
    private void openEmailApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hoanghong4505@gmail.com"});
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}