package glory.manajerpenjualan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PenjelasanPesananActivity extends AppCompatActivity {

    TextView txtid,txtnama,txtnope,txtalamat,txttanggal;
    int jumlahSemua;
    TextView txtTotalsemua;
    private DatabaseReference root,rootList;
    private String temp_namaOutlet;
    private String id,nama,alamat,nope,total,zlist,tanggal;
    private ArrayList<String> list_dari_isi = new ArrayList();
    private ArrayList<String> list_dari_dataBarang = new ArrayList();
    Map<String, Object> newPost = new HashMap<>();
    private HashMap<String,Object> hashDataBarang = new HashMap<>();
    Firebase ref,refOutlet;
    private  ArrayList arrAlamat = new ArrayList();
    private ArrayList arrNama = new ArrayList();
    private ArrayList arrNope = new ArrayList();
    private ArrayList arrTanggal = new ArrayList();
    private ArrayList arrTotal = new ArrayList();
    private ArrayList arrId = new ArrayList();


    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjelasan_pesanan);
        Firebase.setAndroidContext(this);

        temp_namaOutlet = getIntent().getExtras().get("nama_outlet").toString();

        root = FirebaseDatabase.getInstance().getReference().child("outlet").child(temp_namaOutlet);
        rootList = FirebaseDatabase.getInstance().getReference().child("outlet").child(temp_namaOutlet).child("zlist");
        ref =  new Firebase("https://katalog-penjualan.firebaseio.com/outlet");
       // refOutlet = new Firebase("https://katalog-penjualan.firebaseio.com/outlet/"+temp_namaOutlet+"");
        refOutlet = ref.child(temp_namaOutlet);

        txtid = (TextView) findViewById(R.id.txtpenjelID);
        txtnama = (TextView) findViewById(R.id.txtpenjelNama);
        txtnope = (TextView) findViewById(R.id.txtpenjelTelpon);
        txtalamat = (TextView) findViewById(R.id.txtpenjelAlamat);
        txtTotalsemua = (TextView) findViewById(R.id.txtJumlahTotal);
        txttanggal = (TextView) findViewById(R.id.txtpenjelTanggal);


        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list_dari_isi);
        ListView listview =(ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);

       /* root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ambilDataBarang(dataSnapshot);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        ref.orderByChild("nama").equalTo(temp_namaOutlet).addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                for (com.firebase.client.DataSnapshot detil : dataSnapshot.getChildren() ){

                    alamat = (String) detil.child("alamat").getValue();
                    id =  (String) detil.child("id").getValue();
                    nama =  (String) detil.child("nama").getValue();
                    nope =  (String) detil.child("nope").getValue();
                    tanggal =  (String) detil.child("tanggal").getValue();
                    total =  (String) detil.child("totalBayar").getValue();
                    arrAlamat.add(alamat);
                    arrId.add(id);
                    arrNama.add(nama);
                    arrNope.add(nope);
                    arrTanggal.add(tanggal);
                    arrTotal.add(total);
                     Toast.makeText(getApplicationContext(),"alamtnya : "+arrAlamat.get(0).toString(),Toast.LENGTH_SHORT).show();

                    ///
                    txtid.setText(arrId.get(0)+" ");
                    txtalamat.setText(arrAlamat.get(0)+" ");
                    txtnama.setText(arrNama.get(0)+ " ");
                    txtnope.setText(arrNope.get(0)+ " ");
                    txttanggal.setText(arrTanggal.get(0)+ " ");
                    txtTotalsemua.setText(arrTotal.get(0) + " ");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*ref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


                for (com.firebase.client.DataSnapshot child : dataSnapshot.getChildren()){

                    alamat = (String) child.child("alamat").getValue();
                    id =  (String) child.child("id").getValue();
                    nama =  (String) child.child("nama").getValue();
                    nope =  (String) child.child("nope").getValue();
                    total =  (String) child.child("totalBayar").getValue();
                    arrAlamat.add(alamat);
                    arrId.add(id);
                    arrNama.add(nama);
                    arrNope.add(nope);
                    arrTotal.add(total);
                   // Toast.makeText(getApplicationContext(),"alamtnya : "+arrAlamat.get(0).toString(),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"nama : "+arrNama.get(0).toString(),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"total : "+arrTotal.get(0).toString(),Toast.LENGTH_SHORT).show();

                    for (int a=0;a<arrId.size();a++){

                        if (arrId.get(a).equals(id)){

                           /* txtid.setText(id+" ");
                            txtalamat.setText(alamat+" ");
                            txtnama.setText(nama+ " ");
                            txtnope.setText(nope+ " ");
                            txtTotalsemua.setText(total + " ");
                            Toast.makeText(getApplicationContext(),""+arrNama.get(a).toString(),Toast.LENGTH_SHORT).show();

                        }

                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        */

        rootList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ambilDataList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    private void ambilDataList(DataSnapshot dataSnapshot){

        Iterator i = dataSnapshot.getChildren().iterator();
        Set<String> set = new HashSet<String>();

        while (i.hasNext()){

            set.add(((com.google.firebase.database.DataSnapshot) i.next()).getValue().toString());

        }
        list_dari_isi.clear();
        list_dari_isi.addAll(set);
        adapter.notifyDataSetChanged();
    }

    private void ambilDataBarang(DataSnapshot dataSnapshot){

        Iterator i = dataSnapshot.getChildren().iterator();


        Set<String> sut = new HashSet<String>();

        while (i.hasNext()){

            sut.add(((com.google.firebase.database.DataSnapshot) i.next()).getValue().toString());

            alamat  = (String)((DataSnapshot)i.next()).getValue();
            id  = (String)((DataSnapshot)i.next()).getValue();
            nama = (String) ((DataSnapshot)i.next()).getValue();
            nope = (String) ((DataSnapshot)i.next()).getValue();
            hashDataBarang = (HashMap<String, Object>) ((DataSnapshot)i.next()).getValue();
            total = (String) ((DataSnapshot)i.next()).getValue();

        }
        list_dari_dataBarang.clear();
        list_dari_dataBarang.addAll(sut);


        txtid.setText(id+" ");
        txtalamat.setText(alamat+" ");
        txtnama.setText(nama+ " ");
        txtnope.setText(nope+ " ");
        txtTotalsemua.setText(total + " ");

    }
}
