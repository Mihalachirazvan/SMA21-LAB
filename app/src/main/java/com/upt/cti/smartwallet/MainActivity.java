package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Payment;
import ui.AddPaymentActivity;
import ui.PaymentAdapter;

public class MainActivity extends AppCompatActivity  {

    private int currentMonth;
    private List<Payment> payments = new ArrayList<>();
    private DatabaseReference databaseReference;

    private TextView tStatus;
    private Button bPrevious;
    private Button bNext;
    private FloatingActionButton fabAdd;
    private ListView listPayments;
    PaymentAdapter adapter;

    private ValueEventListener databaseListener = null;

    // Firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int REQ_SIGNIN = 3;

    public enum Month {
        January, February, March, April, May, June, July, August,
        September, October, November, December;


        public static Month intToMonthName(int index) {
            return Month.values()[index];
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tStatus = (TextView) findViewById(R.id.tStatus);
        bPrevious = (Button) findViewById(R.id.bPrevious);
        bNext = (Button) findViewById(R.id.bNext);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        listPayments = (ListView) findViewById(R.id.listPayments);
        final PaymentAdapter adapter = new PaymentAdapter(this, R.layout.item_payment, payments);
        listPayments.setAdapter(adapter);

        // setup authentication
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    TextView tLoginDetail = findViewById(R.id.tLoginDetail);
                    TextView tUser = findViewById(R.id.tUser);
                    tLoginDetail.setText("Firebase ID: " + user.getUid());
                    tUser.setText("Email: " + user.getEmail());

                    initPaymentsList();

                    AppState.get().setUserId(user.getUid());
                    attachDBListener(user.getUid());
                } else {
                    startActivityForResult(new Intent(getApplicationContext(),
                            SignupActivity.class), REQ_SIGNIN);
                }
            }
        };
    }
    private void attachDBListener(String uid) {
        // setup firebase database
        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://smart-wallet-f28ef-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference databaseReference = database.getReference();
        AppState.get().setDatabaseReference(databaseReference);

        databaseReference.child("wallet").child(uid).addChildEventListener(new ChildEventListener() {
            //...
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                try {
                    Payment payment = dataSnapshot.getValue(Payment.class);

                    if (payment != null) {
                        payment.timestamp = dataSnapshot.getKey();
//                        AppState.get().updateLocalBackup(getApplicationContext(), payment, true);
                        System.out.println(payment.toString());

                        if (!payments.contains(payment)){
                            payments.add(payment);
                        }

                        adapter.notifyDataSetChanged();
                        listPayments.deferNotifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot,String previousChildName) {

                Payment payment = snapshot.getValue(Payment.class);

                if (payment != null) {
                    payment.timestamp = snapshot.getKey();
                    AppState.get().updateLocalBackup(getApplicationContext(), payment, true);
                    for (Payment p : payments) {
                        if (p.timestamp.equals(payment.timestamp)) {
                            payments.set(payments.indexOf(p), payment);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

                    Payment payment = snapshot.getValue(Payment.class);

                    if (payment != null) {
                        payment.timestamp = snapshot.getKey();
                        AppState.get().updateLocalBackup(getApplicationContext(), payment, true);
                        for (Payment p: payments) {
                            if (p.equals(payment)) {
                                payments.remove(p);
                                break;
                            }
                        }

                        adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot,String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        }) ;

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void clicked(View view){
        switch(view.getId()){
            case R.id.fabAdd:
                AppState.get().setCurrentPayment(null);
                startActivity(new Intent(this, AddPaymentActivity.class));
                break;
            case R.id.bSignOut:
                payments = new ArrayList<>();
                mAuth.signOut();
                break;
        }
    }
    private void initPaymentsList() {
        payments = new ArrayList<>();
        adapter = new PaymentAdapter(this, R.layout.item_payment, payments);
        listPayments.setAdapter(adapter);
    }
}