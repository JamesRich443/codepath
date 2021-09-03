package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    Button button;
    EditText etItem;
    RecyclerView rvitems;
    itemsadapter itemsadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= findViewById(R.id.button);
        etItem=findViewById(R.id.etItem);
        rvitems=findViewById(R.id.rvitems);


        loaditem();
        items.add("buy milk");
        items.add("go to the gym");
        itemsadapter.OnLongClickListener onLongClickListener = new itemsadapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
            items.remove(position);
            itemsadapter.notifyItemRemoved(position);
            Toast.makeText(getApplicationContext(),"item was added",Toast.LENGTH_SHORT).show();
            saveitems();
            }

        };
         itemsadapter itemsAdapter =new itemsadapter(items,onLongClickListener);
        rvitems.setAdapter(itemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            String todoItem= etItem.getText().toString();
            items.add(todoItem);
            itemsAdapter.notifyItemInserted(items.size()-1);
            etItem.setText("");
            Toast.makeText(getApplicationContext(),"item was added",Toast.LENGTH_SHORT).show();
            saveitems();
        }
    });
}

    private void saveitems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("mainActivity", "error writing items");

            e.printStackTrace();
        }

    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
}
private void  loaditem(){
    try {
        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
    } catch (IOException e) {
        Log.e("mainActivity", "error reading items");
        items= new ArrayList<>();

        e.printStackTrace();
    }

}
}