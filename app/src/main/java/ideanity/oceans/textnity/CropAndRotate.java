package ideanity.oceans.textnity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropAndRotate extends AppCompatActivity implements View.OnClickListener
{

    private Toolbar toolbar;
    private FloatingActionButton mFab;
    public static Bitmap croppedImage;
    private String message;
    CropImageView cropImageView;
    ImageView rotate_left, rotate_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_and_rotate);

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setElevation(toolbar,10);
        toolbar.setOnMenuItemClickListener(this);*/

        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);

        rotate_left = findViewById(R.id.rotate_left);
        rotate_right = findViewById(R.id.rotate_right);

        cropImageView.setImageUriAsync(Uri.parse(message));
        mFab = (FloatingActionButton) findViewById(R.id.nextStep);
        mFab.setOnClickListener(this);

        rotate_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(-90);
            }
        });

        rotate_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(90);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextStep){
            cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
                @Override
                public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                    croppedImage = result.getBitmap();
                    Intent intent = new Intent(CropAndRotate.this, Binarization.class);
                    startActivity(intent);
                }
            });
            cropImageView.getCroppedImageAsync();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rotate, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rotate_left:
                cropImageView.rotateImage(-90);
                break;
            case R.id.rotate_right:
                cropImageView.rotateImage(90);
                break;
            case R.id.crop_image:
                cropImageView.getCroppedImageAsync();
                break;
        }
        return false;
    }*/

}
