package jp.ac.ecc.se.yai_todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.prefs.Preferences;

public class TodoList extends AppCompatActivity {
    final int CREATE_RESULT = 1;
    ArrayList<String> dataListArray = new ArrayList<>(); // data array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        SharedPreferences pref = getSharedPreferences("saveData",Context.MODE_PRIVATE);

        Map<String, ?> str = pref.getAll();

        String strTitle = (String) str.get("title");

        if (strTitle!=null){

        if (!strTitle.equals("")) {
            String[] arrayTitle = strTitle.split(",");
            for (String data : arrayTitle) {
                dataListArray.add(data);
            }
        }}

        ArrayAdapter<String> adapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataListArray);
        ListView listView = findViewById(R.id.dataList);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent viewIntent = new Intent(getApplicationContext(),viewTodo.class);

                //ViewActivity にタイトル送る
                String st=(String) adapterView.getItemAtPosition(i);
                viewIntent.putExtra("title",st);

                //ViewActivity にメモを送る
                String memo = pref.getString("memo","");
                String [] arrayMemo = memo.split(",");
                viewIntent.putExtra("memo",arrayMemo[i]);


                String image = pref.getString("image","");
                String [] arrayImage = image.split(",");
                viewIntent.putExtra("image",arrayImage[i]);

                startActivity(viewIntent);
            }
        });

    }

    //option menuBar settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intentCreate = new Intent(this, CreateActivity.class);
        switch (item.getItemId()){

            case R.id.ABaddnew:
            case R.id.ABnew:
                Toast.makeText(this, "Add New", Toast.LENGTH_SHORT).show();
                startActivityForResult(intentCreate, CREATE_RESULT);
                return true;

            case R.id.ABclear:
            case R.id.ABreset:
                Toast.makeText(this, "Reset All", Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getSharedPreferences("saveData",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                finish();
                return true;

            case R.id.ABexit:
                finish();

            default:
                return super.onOptionsItemSelected(item);

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode+"   "+resultCode);
        if(requestCode == CREATE_RESULT){

            ListView dataListView= findViewById(R.id.dataList);

            ArrayAdapter<String> adapter =
                    new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataListArray);

            SharedPreferences pref = getSharedPreferences("saveData",Context.MODE_PRIVATE);

            String a = data.getStringExtra("title");
            //Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
             dataListArray.add(a);
//             pref.getAll();
             dataListView.setAdapter(adapter);
        }
    }
}
