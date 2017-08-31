package akash.amit.ashutosh.toast;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BabaTV extends AppCompatActivity {
    ArrayList<ImageUpload> imgList;
    private LinearLayoutManager lLayout;
    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baba_tv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView iv= (ImageView) findViewById(R.id.imageView2);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Breaking News");
        progress.setMessage("connecting...");
        progress.show();
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
                Toast.makeText(BabaTV.this, "loading...", Toast.LENGTH_SHORT).show();
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);

        imgList= new ArrayList<>();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                    Log.i("contact:: ", img.title + " " + img.description+" "+img.url);
                }
                //Recycler View
                lLayout = new LinearLayoutManager(BabaTV.this);
                RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view1);
                rView.setLayoutManager(lLayout);
                BabaTvRecyclerViewAdapter rcAdapter = new BabaTvRecyclerViewAdapter(BabaTV.this, imgList);
                rView.setAdapter(rcAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
