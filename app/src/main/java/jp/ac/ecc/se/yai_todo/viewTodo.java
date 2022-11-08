package jp.ac.ecc.se.yai_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

public class viewTodo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_todo);
        TextView titleView = findViewById(R.id.titleView);
        TextView memoView = findViewById(R.id.memoView);
        ImageView imageView = findViewById(R.id.photoView);


        SharedPreferences pref = getSharedPreferences("saveData", Context.MODE_PRIVATE);


         Intent intent = getIntent();

         String title= intent.getStringExtra("title");
         titleView.setText(title);

         String memo = intent.getStringExtra("memo");
         memoView.setText(memo);

        String image = intent.getStringExtra("image");
        Uri imageUri= Uri.parse(image);
        imageView.setImageURI(imageUri);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendImage = new Intent(getApplicationContext(), ImagenViewActivity.class);
                sendImage.putExtra("image", imageUri);
                if (imageUri!=null)
                    startActivity(sendImage);
            }
        });


    }
}

