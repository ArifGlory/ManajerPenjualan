package glory.manajerpenjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private DatabaseReference root;
    private ArrayList<String> list_dari_outlet = new ArrayList();
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = FirebaseDatabase.getInstance().getReference().child("outlet");
        listView = (ListView) findViewById(R.id.listview1);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_dari_outlet);
        listView.setAdapter(arrayAdapter);

        root.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((com.google.firebase.database.DataSnapshot) i.next()).getKey());
                }
                list_dari_outlet.clear();
                list_dari_outlet.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //disini ada intent ke penjelasan pegawai
                Intent i = new Intent(getApplicationContext(), PenjelasanPesananActivity.class);
                i.putExtra("nama_outlet", ((TextView) view).getText().toString());
                startActivity(i);

            }
        });



    }
}
