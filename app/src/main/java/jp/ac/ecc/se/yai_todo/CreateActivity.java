package jp.ac.ecc.se.yai_todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {
    final int CAMERA_RESULT =100; //camera result
    Uri imageUri;//image from gallery

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //
        String saveData= "";

        //variables
        FloatingActionButton AddImageButton = findViewById(R.id.AddImageButton);
        ImageView imageView1= findViewById(R.id.imageView1);
        Button saveButton= findViewById(R.id.saveButton);
        EditText titleInput = findViewById(R.id.titleInput);
        EditText memoInput = findViewById(R.id.memoInput);

        //Intent's
        Intent sendImage = new Intent(getApplicationContext(), ImagenViewActivity.class);
        Intent TodoListActivity =  new Intent(this, TodoList.class);

        SharedPreferences pref = getSharedPreferences("saveData",Context.MODE_PRIVATE);


        //save button preferences
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(titleInput.getText().toString().equals(""))
                    Toast.makeText(CreateActivity.this, "タイトルを入力してください", Toast.LENGTH_SHORT).show();
                else if (memoInput.getText().toString().equals(""))
                    Toast.makeText(CreateActivity.this, "メモを入力してください", Toast.LENGTH_SHORT).show();
                else if (imageUri==null)
                    Toast.makeText(CreateActivity.this, "写真を撮ってください", Toast.LENGTH_SHORT).show();
                else {

                    //title preferences
                    SharedPreferences.Editor editor = pref.edit();
                    String title = pref.getString("title", "");
                    title += titleInput.getText().toString() + ",";
                    editor.putString("title", title);
                    editor.apply();

                    Intent intent = new Intent();
                    intent.putExtra("title", titleInput.getText().toString());


                    //memo preferences

                    SharedPreferences.Editor memoEditor = pref.edit();
                    String memo = pref.getString("memo", "");

                    memo += memoInput.getText().toString() + ",";
                    memoEditor.putString("memo", memo);
                    memoEditor.apply();

                    if (imageUri!=null){
                        //image preferences
                        Uri imageStr= imageUri;
                        SharedPreferences.Editor imageEditor = pref.edit();
                        String image = pref.getString("image","");

                        image += imageStr.toString() + ",";
                        imageEditor.putString("image", image);
                        imageEditor.apply();
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        //full image view activity
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendImage.putExtra("image", imageUri);
                if (imageUri!=null)
                    startActivity(sendImage);
            }
        });


        //Capture Image
        AddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create file
                String timestamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
                String fileName ="memo_"+timestamp+"_.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");

                //create URI
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //capture image instance
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_RESULT);

            }
        });

    }
    // set captured image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_RESULT && resultCode == RESULT_OK){
            ImageView imageView1= findViewById(R.id.imageView1);
            imageView1.setImageURI(imageUri);

        }
    }
}