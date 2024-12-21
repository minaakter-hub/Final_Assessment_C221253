package com.example.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class camera extends AppCompatActivity {
    ImageView img;
    Button btn,btn2;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_TAKE_PHOTO = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);

        img=findViewById(R.id.img);
        btn=findViewById(R.id.button);
        btn2=findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setAction(Intent.ACTION_PICK);
                intent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1,REQUEST_TAKE_PHOTO);

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_IMAGE_CAPTURE){
                Bitmap bitmap=(Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bitmap);//rgb pixel akare 1024*1024 pixel kore nibe
                SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profileImageUri", data.getData().toString());
                editor.apply();
                Intent intent = new Intent(camera.this, MainActivity.class);
                startActivity(intent);
            }
        }
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_TAKE_PHOTO){
                img.setImageURI(data.getData());//uri ta nicci getdata diye

                SharedPreferences sharedPreferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profileImageUri", data.getData().toString());
                editor.apply();
                Intent intent = new Intent(camera.this,MainActivity.class);
                startActivity(intent);
            }
        }
    }
}