package com.example.waterlevelmonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.integrity.v;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button,buttonOn, buttonOff;
    TextView textView,textView1,textView2,textView3;

    DatabaseReference motorControlRef;

    FirebaseUser user;
   // Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView =findViewById(R.id.mainpage);
        textView1=findViewById(R.id.txtwaterlevel);
        buttonOn=findViewById(R.id.btnon);
        buttonOff=findViewById(R.id.btnoff);
        textView2=findViewById(R.id.waterstatus);
        textView3=findViewById(R.id.txttankcapacity);
        user = auth.getCurrentUser();

        motorControlRef = FirebaseDatabase.getInstance().getReference().child("Buttons");
        if(user==null)
        {
            Intent intent= new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }
        else
        {
           textView.setText(user.getEmail());
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Watersensors");
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Button");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String liters = snapshot.child("WaterLevel").getValue().toString();
                        textView1.setText(liters);
                        String tankcapacity = snapshot.child("emptytankdistance").getValue().toString();
                        textView3.setText(tankcapacity);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String motorval = snapshot.child("motorstatus").getValue().toString();
                        textView2.setText(motorval);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            // Set switch change listeners to update values in Firebase

            buttonOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference("Button").child("motorbutton").setValue(1);
                }
            });
            buttonOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference("Button").child("motorbutton").setValue(0);
                }
            });


        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}