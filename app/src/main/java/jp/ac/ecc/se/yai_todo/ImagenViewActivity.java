package jp.ac.ecc.se.yai_todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImagenViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_view);

        ImageView fullImageView = findViewById(R.id.fullImageView);
        Intent intent = getIntent();
        Uri imageUri = (intent!=null)?intent.getParcelableExtra("image"):null;
        fullImageView.setImageURI(imageUri);
    }
}