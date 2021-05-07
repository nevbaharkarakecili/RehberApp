package com.example.rehberim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private  ConstraintLayout pLayout;
    private  PopupWindow popupWindow;
    private Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FloatingActionButton fab = findViewById(R.id.fab);
        final Button getir =findViewById(R.id.getir);
        final Button update = findViewById(R.id.update);

        //final TextView listele = findViewById(R.id.listele);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent form = new Intent(getApplicationContext(),FormActivity.class);
                startActivity(form);
            }
        });


        public void fetchData(View view ){

        RehberDB db =new RehberDB(getApplicationContext());
        db.connectRehber();
        ArrayList<HashMap<String,String>> data = db.dataGetir();

        ListAdapter adapter =new SimpleAdapter(
                MainActivity.this,
                data,
        R.layout.kisilerim_item,
        new String[]{"_id","ad","soyad","tel"},
                new int[]{R.id.id,R.id.name,R.id.surname,R.id.phone}
        );
        final ListView lv = findViewById(R.id.Kisilerim);
        lv.setAdapter(adapter);
        db.disconnectRehber();
        /*getir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RehberDB db = new RehberDB(getApplicationContext());
                db.connectRehber();
                //String liste = db.dataGetir();
                //listele.setText(liste);

            }
        });*/

    }






            LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

            View pView = inflater.inflate(R.layout.kisi,null);

            popupWindow = new PopupWindow(
                    pView,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(10.0f);
            }

            final TextView pName = pView.findViewById(R.id.singleName);
            final TextView pSurname = pView.findViewById(R.id.singleSurname);
            final TextView pID = pView.findViewById(R.id.singleID);
            final TextView pPhone = pView.findViewById(R.id.singlePhone);

            String id = ((TextView) view.findViewById(R.id.id)).getText().toString();
            String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
            String surname = ((TextView) view.findViewById(R.id.surname)).getText().toString();
            String phone = ((TextView) view.findViewById(R.id.phone)).getText().toString();


            pID.setText(id);
            pName.setText(name);
            pSurname.setText(surname);
            pPhone.setText(phone);

            close = pView.findViewById(R.id.close);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            Button sil = pView.findViewById(R.id.sil);
            sil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RehberDB db = new RehberDB(getApplicationContext());
                    if(!pID.getText().toString().isEmpty()){

                        db.connectRehber();
                        long _id= Integer.valueOf(pID.getText().toString());
                        db.sil (_id) ;
                        db.disconnectRehber();
                        LayoutInflater inflaterMain = (LayoutInflater)MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                        View mView =inflaterMain.inflate(R.layout.activity_main,null);
                    }


                    popupWindow.dismiss();
                }
            });

            Button update = pView.findViewById(R. id.update);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent form = new Intent(getApplicationContext(),FormActivity.class);
                    long _id =Long.valueOf(pID.getText().toString());
                    String _name =pName.getText().toString();
                    String _surname =pSurname.getText().toString();
                    String _tel = pPhone.getText().toString();

                    form.putExtra("recordId",_id);
                    form.putExtra("recordName",_name);
                    form.putExtra("recordSurname",_surname);
                    form.putExtra("recordPhone",_tel);

                    startActivity(form);
                    popupWindow.dismiss();
                }
            });

            popupWindow.showAtLocation(findViewById(R.id.cl), Gravity.CENTER,0,0);


        }

}
