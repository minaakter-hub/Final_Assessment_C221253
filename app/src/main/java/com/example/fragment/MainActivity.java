package com.example.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
Button btn1,btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn1=findViewById(R.id.button1);
        btn2=findViewById(R.id.button2);
        btn3=findViewById(R.id.button3);
        callFragment(new FirstFragment(),0);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           callFragment(new FirstFragment(),1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
callFragment(new SecondFragment(),1);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
callFragment(new ThirdFragment(),1);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void callFragment(Fragment fragment,int status) {

        FragmentManager fragmentManager=getSupportFragmentManager();

       FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
       if(status==0)
       {
           fragmentTransaction.add(R.id.container,fragment);
       }
       else if(status==1)
       {
           fragmentTransaction.replace(R.id.container,fragment);
    }
       fragmentTransaction.commit();

    }
}