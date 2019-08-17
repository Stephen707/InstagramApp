package com.example.instagramapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private final String APP_TAG = "Grand";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public String photoFileName = "photo.jpg";
    private File photoFile;
    Button btn_picture,btn_submit;
    EditText et_Desc;
    ImageView iv_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_picture = findViewById(R.id.btn_photo);
        btn_submit = findViewById(R.id.btn_submit);
        et_Desc = findViewById(R.id.et_description);
        iv_picture = findViewById(R.id.picture);


        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = et_Desc.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter a description", Toast.LENGTH_SHORT).show();
                }else {
                    ParseUser user = ParseUser.getCurrentUser();
                    if (photoFile == null || iv_picture.getDrawable() == null){
                        Log.d(TAG,"no Photo");
                        Toast.makeText(MainActivity.this, "There is no photo", Toast.LENGTH_SHORT).show();
                    }
                    savePost(description,user,photoFile);
                }
            }
        });
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(this,"com.codepath.fileprovider",photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider);
        if (intent.resolveActivity(getPackageManager()) != null ){
            startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    public File getPhotoFileUri(String fileName){
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),APP_TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG,"failled to create Directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                iv_picture.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void savePost(String description, ParseUser parseUser, File photoFile) {
        Post post = new Post();
        post.setDescription(description);
        post.setUser(parseUser);
        post.setImage(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e !=  null){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,"Error while saving");
                    e.printStackTrace();
                    return;
                }
                et_Desc.setText("");
                iv_picture.setImageResource(0);
                Toast.makeText(MainActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
