package com.example.jonathansun5.photoannotation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private DrawableView drawable_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // generate image bitmap
        Bitmap b;
        ImageView img = (ImageView) findViewById(R.id.imageView2);
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            img.setImageBitmap(b);
        }
        // generate spinner for draw and erase
        Spinner chooseAction = (Spinner)findViewById(R.id.spinner);
        ArrayList<String> actions = new ArrayList<String>();
        actions.add("Draw");
        actions.add("Erase");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, actions);
        chooseAction.setAdapter(adapter);
        chooseAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "Action set to " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                if (adapterView.getItemAtPosition(i).equals("Draw")) {
                    drawable_view = (DrawableView) findViewById(R.id.drawable_view);
                    drawable_view.setDrawingEnabled(true);
                    drawable_view.onClickEraser(false);
                } else {
                    drawable_view = (DrawableView) findViewById(R.id.drawable_view);
                    drawable_view.setDrawingEnabled(true);
                    drawable_view.onClickEraser(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // generate spinner for brush size
        Spinner chooseSize = (Spinner)findViewById(R.id.spinner2);
        ArrayList<String> sizes = new ArrayList<String>();
        sizes.add("Large");
        sizes.add("Medium");
        sizes.add("Small");
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sizes);
        chooseSize.setAdapter(adapter2);
        chooseSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "Brush/Eraser size set to " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                if (adapterView.getItemAtPosition(i).equals("Large")) {
                    drawable_view = (DrawableView) findViewById(R.id.drawable_view);
                    drawable_view.setDrawingEnabled(true);
                    drawable_view.setBrushEraserSize(100);
                } else if (adapterView.getItemAtPosition(i).equals("Medium")) {
                    drawable_view = (DrawableView) findViewById(R.id.drawable_view);
                    drawable_view.setDrawingEnabled(true);
                    drawable_view.setBrushEraserSize(50);
                } else {
                    drawable_view = (DrawableView) findViewById(R.id.drawable_view);
                    drawable_view.setDrawingEnabled(true);
                    drawable_view.setBrushEraserSize(10);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // generate clear canvas button
        Button clearButton = (Button)findViewById(R.id.button3);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable_view.clearCanvas();
                Toast.makeText(getApplicationContext(), "Cleared the Drawing", Toast.LENGTH_SHORT).show();
            }
        });
        // generate color selection seeker bar
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
                float[] hsvColor = {0, 1, 1};
                // generate only hue component in range [0, 360),
                // leaving saturation and brightness maximum possible
                hsvColor[0] = 360f * progress / seekBar.getMax();
                drawable_view.setPaintColor(Color.HSVToColor(hsvColor));
                Toast.makeText(getApplicationContext(), "Changed the Color to " + Color.HSVToColor(hsvColor), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
