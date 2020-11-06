package ideanity.oceans.textnity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.googlecode.leptonica.android.GrayQuant;
import com.googlecode.leptonica.android.Pix;

import java.util.Arrays;

public class Binarization extends AppCompatActivity implements View.OnClickListener, AppCompatSeekBar.OnSeekBarChangeListener

{

    private ImageView img;
    private Toolbar toolbar;
    private AppCompatSeekBar seekBar;
    private Pix pix;
    private FloatingActionButton fab;
    public static Bitmap umbralization;
    private Spinner spinner;
    public static int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binarization);

        //ViewCompat.setElevation((LinearLayout) findViewById(R.id.extension),10);
        spinner = (Spinner) findViewById(R.id.language);

        img = (ImageView) findViewById(R.id.croppedImage);
        fab = (FloatingActionButton) findViewById(R.id.nextStep);
        fab.setOnClickListener(this);
        pix = com.googlecode.leptonica.android.ReadFile.readBitmap(CropAndRotate.croppedImage);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Arrays.asList("English", "French", "Arabic"));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) view).setTextColor(Color.WHITE);
                language = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        IdeanityThresholder ideanityThresholder = new IdeanityThresholder();
        int threshold = ideanityThresholder.doThreshold(pix.getData());
        /* increase threshold because is better*/
        threshold += 20;
        umbralization = com.googlecode.leptonica.android.WriteFile.writeBitmap(GrayQuant.pixThresholdToBinary(pix,threshold));
        img.setImageBitmap(umbralization);
        seekBar = (AppCompatSeekBar) findViewById(R.id.umbralization);
        seekBar.setProgress(Integer.valueOf((50 * threshold)/254));
        seekBar.setOnSeekBarChangeListener(this);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        umbralization = com.googlecode.leptonica.android.WriteFile.writeBitmap(
                GrayQuant.pixThresholdToBinary(pix,Integer.valueOf(((254 * seekBar.getProgress())/50)))
        );
        img.setImageBitmap(umbralization);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextStep) {
            Intent intent = new Intent(Binarization.this, Recognizer.class);
            startActivity(intent);
        }

    }
}

