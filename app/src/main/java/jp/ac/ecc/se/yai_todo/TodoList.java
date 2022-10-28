package jp.ac.ecc.se.yai_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TodoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
       // return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intentCreate = new Intent(this, CreateActivity.class);
        switch (item.getItemId()){

            case R.id.ABaddnew:
            case R.id.ABnew:
                Toast.makeText(this, "Add New", Toast.LENGTH_SHORT).show();
                startActivity(intentCreate);
                return true;

            case R.id.ABclear:
                Toast.makeText(this, "Clear", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ABreset:
                Toast.makeText(this, "Reset All", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ABexit:
                finish();

            default:
                return super.onOptionsItemSelected(item);

        }
        //return super.onOptionsItemSelected(item);
    }
}