package com.example.francois.priape;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.api.Default;
import com.example.francois.priape.databinding.ActivityRegisterBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Bitmap bitmap = null, rotatedBitmap = null;
    private CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        binding.registerPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForPicture();
            }
        });


        //Initialise toolbar element
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.register));
        //Set back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void Register(View view)
    {
        final MaterialDialog progress = new MaterialDialog.Builder(RegisterActivity.this)
                .content(R.string.progress_inscription)
                .progress(true, 0)
                .show();
        if(rotatedBitmap != null)
        {

            API.uploadFile(persitImage(rotatedBitmap, binding.RegisterName.getText().toString()+binding.RegisterEmail.getText().toString(), RegisterActivity.this.getFilesDir()), new Callback.UploadFileCallback() {
                @Override
                public void success(String url) {
                    createUser(url);
                    progress.dismiss();
                }

                @Override
                public void error(String errorCode) {
                    progress.dismiss();
                }
            });
        }
        else{
            createUser(null);
        }
    }

    private void createUser(String picture)
    {
        final MaterialDialog progress = new MaterialDialog.Builder(RegisterActivity.this)
                .content(R.string.progress_inscription)
                .progress(true, 0)
                .show();
        if(binding.RegisterPassword.getText().toString().equals(binding.RegisterRepassword.getText().toString())) {
            API.Register(binding.RegisterEmail.getText().toString(), binding.RegisterName.getText().toString(), binding.RegisterPassword.getText().toString(), picture, new Callback.RegisterCallback() {
                        @Override
                        public void success() {
                            progress.dismiss();
                            finish();
                            Toast.makeText(getApplicationContext(), getString(R.string.registerSuccess), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void error(String errorCode) {
                            progress.dismiss();
                            new MaterialDialog.Builder(RegisterActivity.this)
                                    .content(getString(R.string.error_others))
                                    .positiveText(android.R.string.ok)
                                    .show();

                        }
                    }
            );
        }else
        {
            new MaterialDialog.Builder(RegisterActivity.this)
                    .content(R.string.badPassword)
                    .positiveText(android.R.string.ok)
                    .show();
        }
    }

    //Toolbar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true; }

            return super.onOptionsItemSelected(item);

    }
    private void askForPicture()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Default.SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Default.SELECT_PICTURE && resultCode == RESULT_OK && null != data)
        {
            bitmap = null;
            rotatedBitmap = null;
            InputStream inputStream;
            String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
            Cursor cur = getContentResolver().query(data.getData(), orientationColumn, null, null, null);
            int orientation = -1;
            if (cur != null && cur.moveToFirst()) {
                orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                cur.close();
            }

            Picasso.with(this).load(data.getData()).rotate(setDegreeRot(orientation)).fit().centerCrop().into(binding.registerPicture);

            try {
                inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }

            if(bitmap != null) {

                Bitmap resize = Bitmap.createScaledBitmap(bitmap, 1280, 720, true);
                Matrix matrix = new Matrix();
                matrix.postRotate(setDegreeRot(orientation));
                rotatedBitmap = Bitmap.createBitmap(resize, 0, 0, resize.getWidth(), resize.getHeight(), matrix, true);

            }

        }
    }
    public int setDegreeRot(int ori) {
        switch (ori) {
            case 90:
                return 90;
            case 180:
                return -180;
            case 270:
                return -90;
            default:
                return 0;

        }
    }
    private static File persitImage(Bitmap bitmap, String name, File dir)
    {
        File imageFile = new File(dir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("testexep",e.getMessage());
        }

        return imageFile;
    }
}
