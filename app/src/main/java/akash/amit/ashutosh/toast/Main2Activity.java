package akash.amit.ashutosh.toast;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private EditText editText_date,editText_title,editText_description,editText_link;
    private Uri imguri;
    public static final String FB_Storage_Path = "image/";
    public static final String FB_Database_Path = "image";
    public static final int Request_Code = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        Button btn= (Button) findViewById(R.id.button_out);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        imageView = (ImageView) findViewById(R.id.imageView);
        editText_date = (EditText) findViewById(R.id.txtImageDate);
        editText_title = (EditText) findViewById(R.id.txtImageTitle);
        editText_description = (EditText) findViewById(R.id.txtImageDescription);
        editText_link = (EditText) findViewById(R.id.txtImageLink);
    }


    public void btnBrowse_Click(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), Request_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            Log.i("yo",String.valueOf(imguri));
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//to get extension type
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View v) {
        if (imguri != null && editText_title.getText()  !=null && editText_description.getText()  !=null && editText_date.getText()  !=null && editText_link.getText()  !=null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading image");
            dialog.show();

            //Get the storage reference
            StorageReference ref = storageReference.child(FB_Storage_Path + System.currentTimeMillis() + "." + getImageExt(imguri));

            //Add file to reference
            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Dimiss dialog when success
                    dialog.dismiss();
                    //Display success toast msg
                    Toast.makeText(getApplicationContext(), "News Submitted Successfully", Toast.LENGTH_SHORT).show();
                    //we can also use hash map here..................
                    //ImageUpload imageUpload = new ImageUpload(editText_date.getText().toString(),editText_title.getText().toString(),editText_description.getText().toString(),editText_link.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                    ImageUpload imageUpload=new ImageUpload();
                    imageUpload.date=editText_date.getText().toString();
                    imageUpload.title=editText_title.getText().toString();
                    imageUpload.description=editText_description.getText().toString();
                    imageUpload.link=editText_link.getText().toString();
                    imageUpload.url=taskSnapshot.getDownloadUrl().toString();
                    //Save image info in to firebase database
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(imageUpload);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //Show upload progress
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
        }
    }
    private void signOut() {
        mAuth.signOut();
    }
}
