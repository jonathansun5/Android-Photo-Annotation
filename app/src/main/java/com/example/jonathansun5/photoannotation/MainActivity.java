package com.example.jonathansun5.photoannotation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static ImageView iView;
    public static Bitmap imageBitmap;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onCameraClick(View view) {
        dispatchTakePictureIntent();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api= Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap)extras.get("data");
            iView = (ImageView)findViewById(R.id.imageView);
            iView.setImageBitmap(imageBitmap);
//            ByteArrayOutputStream bsa = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bsa);
//            Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bsa.toByteArray()));
////            Bitmap imageBitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
//            iView = findViewById(R.id.imageView);
//            Drawable drawable = new BitmapDrawable(getResources(), decoded);
//
//            iView.setImageDrawable(drawable);
////            iView.setImageBitmap(imageBitmap);
        }
    }

    public void onContinueClick(View view) {
//        Intent intent = new Intent(this, Main2Activity.class);
//        intent.putExtra("resId", imageBitmap);
//        startActivity(intent);

        Intent i = new Intent(this, Main2Activity.class);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bs);
        i.putExtra("byteArray", bs.toByteArray());
        startActivity(i);


//        Intent intent = new Intent(this, Main2Activity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("resId", imageBitmap);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }
}
