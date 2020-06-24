package com.example.simpletodo;

import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button buttonAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonAdd = findViewById(R.id.buttonAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();
        items.add("Do my homework");
        items.add("Study cs");
        items.add("Play soccer");
        items.add("Get ice-cream :)");

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //delete item from model
                items.remove(position);
                //notify adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.maketext(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        itemsAdapter = new ItemsAdapter(items,onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.SetLayoutManager(new LinearLayoutManager(context: this));

        buttonAdd.setOnClickListener(new View.OnClickListener()) {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();

                //add item to the model
                items.add(todoItem);
                //Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() -1);
                etItem.setText("");
                Toast.maketext(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }
    private File getDataFile() {
        return new File(getFilesDir()), child: "data.txt");
    }
    //function that will load items by reading every line of the data file
    private void loadItems() {
        items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
    } catch (IOException e) {
        e.printStackTrace();
        log.e(tag: "MainActivity", msg: "Error reading items", e);
        items = new ArrayList<>();
    }


    //function saves items by writing them into the data file
    private void saveItems() {
        FileUtils.writeLines(getDataFile(), items);
    } catch (IOException e){
        log.e(tag:   "MainActivity",msg:"Error reading items",e);
    }
}

