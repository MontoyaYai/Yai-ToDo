package jp.ac.ecc.se.yai_todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.prefs.Preferences;

public class TodoList extends AppCompatActivity {
    final int CREATE_RESULT = 1;
    ArrayList<String> dataListArray = new ArrayList<>(); // data array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        //preferences
        SharedPreferences pref = getSharedPreferences("saveData",Context.MODE_PRIVATE);
        //set title at task
        Map<String, ?> str = pref.getAll();
        String strTitle = (String) str.get("title");
        if (strTitle!=null){
            if (!strTitle.equals("")) {
                String[] arrayTitle = strTitle.split(",");
                for (String data : arrayTitle) {
                    dataListArray.add(data);
                }
            }
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataListArray);

        ListView listView = findViewById(R.id.dataList);

        listView.setAdapter(adapter);//Set array to listView
        registerForContextMenu(listView);//Set menu to listItem

        //Select item →　View Todoに表示　
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

                //ViewActivity に写真を送る
                String image = pref.getString("image","");
                String [] arrayImage = image.split(",");
                viewIntent.putExtra("image",arrayImage[i]);
                startActivity(viewIntent);
            }
        });
        }

        //task menu
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("TASK MENU");
            getMenuInflater().inflate(R.menu.check_menu, menu);
        }
        //task menu Switch
        @Override
        public boolean onContextItemSelected(@NonNull MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            switch(item.getItemId()){

                case R.id.deleteTask:

                    SharedPreferences pref = getSharedPreferences("saveData",Context.MODE_PRIVATE);
                    //set title at task

                    //title delete
                    Map<String, ?> str = pref.getAll();
                    String strTitle = (String) str.get("title");
                    String[] arrayTitle = strTitle.split(",");
                    ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arrayTitle));
                    arrayList.remove(arrayList.get((int)info.id));
                    String s = String.join(",", arrayList);

                    //memo delete
                    String strMemo = (String)str.get("memo");
                    String [] memoArray= strMemo.split(",");
                    ArrayList<String> memoList = new ArrayList<String>(Arrays.asList(memoArray));
                    memoList.remove(memoList.get((int)info.id));
                    String m= String.join(",",memoList);

                    //image delete
                    String strImage = (String)str.get("image");
                    String [] imageArray =strImage.split(",");
                    ArrayList<String> imageList = new ArrayList<String>(Arrays.asList(imageArray));
                    imageList.remove(imageList.get((int)info.id));
                    String i = String.join(",",imageList);

                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("title", s + ",");
                    editor.putString("memo",m+",");
                    editor.putString("image",i+",");

                    editor.apply();

                    dataListArray.clear();

                    if (strTitle!=null){
                        if (!strTitle.equals("")) {
                            for (String data : arrayList) {
                                dataListArray.add(data);
                            }
                        }
                    }
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataListArray);

                    ListView listView = findViewById(R.id.dataList);

                    listView.setAdapter(adapter);
                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                    return true;
//                case R.id.editTask:
//                    Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
//                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        //option menuBar settings
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu1,menu);
            return true;
        }
        //Option menuBar Switch
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            Intent intentCreate = new Intent(this, CreateActivity.class);
            switch (item.getItemId()){
                case R.id.ABaddnew:
                    Toast.makeText(this, "Add New", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intentCreate, CREATE_RESULT);
                    return true;
                case R.id.ABclear:
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
        //Create Activity For Result
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
                 dataListArray.add(a);
                 dataListView.setAdapter(adapter);
            }
        }
}
