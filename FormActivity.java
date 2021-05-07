package com.example.rehberim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        final TextView formAd = findViewById(R.id.FormAd);
        final TextView formSoyad = findViewById(R.id.FormSoyad);
        final TextView formTel = findViewById(R.id.FormTel);
        final Button formKaydet = findViewById(R.id.FormKaydet);
        long RecordId=0;
        Bundle extra=getIntent().getExtras();
        final long finalRecordId= RecordId;
        RehberDB db= new RehberDB(getApplicationContext());
        db.connectRehber();


        formSoyad.setText("recordSurname");
        formAd.setText("recordName");
        formTel.setText("recordPhone");


        formKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RehberDB db = new RehberDB(getApplicationContext());
                db.connectRehber();
                long result = db.ekle(
                        formAd.getText().toString(),
                        formSoyad.getText().toString(),
                        formTel.getText().toString(),
                        "",
                        ""
                );

                db.disconnectRehber();
                String cevap = String.valueOf(result);
                Toast.makeText(getApplicationContext(), cevap, Toast.LENGTH_LONG).show();



            }
        });
    }
}
