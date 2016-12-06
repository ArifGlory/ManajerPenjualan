package glory.manajerpenjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText nama, pass ;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nama = (EditText) findViewById(R.id.etuser);
        pass = (EditText) findViewById(R.id.etpass);



    }

    public void klikLogin(View view) {

        if (nama.getText().toString().equals("manajer") && pass.getText().toString().equals("manajer") ){

            i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);

        }else {

            Toast.makeText(getApplicationContext(),"Cek username / password anda",Toast.LENGTH_SHORT).show();

        }





    }
}
