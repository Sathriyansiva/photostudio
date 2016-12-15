
package com.example.ssboss.inkedstudio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
Button selectimage;
    StorageReference store;
    private static final int GALLARY_INTENT=2;
    private ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        store= FirebaseStorage.getInstance().getReference();
        selectimage=(Button)findViewById(R.id.selectimage);
        pdialog=new ProgressDialog(this);
        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLARY_INTENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
              if(requestCode==GALLARY_INTENT && resultCode==RESULT_OK) {
                  pdialog.setMessage("Uploading...");
                  pdialog.show();
                  Uri uri = data.getData();
                  StorageReference filepath = store.child("photos").child(uri.getLastPathSegment());
                  filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          Toast.makeText(MainActivity.this, "upload done", Toast.LENGTH_SHORT).show();
                          pdialog.dismiss();
                      }
                  });
              }
    }
}
