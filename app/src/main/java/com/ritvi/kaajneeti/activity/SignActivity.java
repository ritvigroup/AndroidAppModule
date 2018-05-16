package com.ritvi.kaajneeti.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

public class SignActivity extends AppCompatActivity implements View.OnClickListener  {
    Button donebutton, clearButton;
    //EditText editText;
    TextView txtBlack, txtRed, txtBlue;
    SignaturePad mSignaturePad;
    //	 private CanvasView customCanvas;
    String path;
    File filename = null;
    Bitmap bitmap;
    public static Activity mActivity;
    String result = "";
    String file = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);


//		customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        donebutton = (Button) findViewById(R.id.DoneButton);
        clearButton = (Button) findViewById(R.id.ClearButton);
        clearButton = (Button) findViewById(R.id.ClearButton);
        txtBlack = (TextView) findViewById(R.id.txt_black);
        txtRed = (TextView) findViewById(R.id.txt_red);
        txtBlue = (TextView) findViewById(R.id.txt_blue);

        donebutton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        txtBlack.setOnClickListener(this);
        txtRed.setOnClickListener(this);
        txtBlue.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("sign.txt", Context.MODE_PRIVATE);
        String color = sp.getString("colorformat", "");

        switch (color) {
            case "black":
                mSignaturePad.setPenColor(Color.BLACK);
                break;
            case "blue":
                mSignaturePad.setPenColor(Color.BLUE);
                break;
            case "red":
                mSignaturePad.setPenColor(Color.RED);
                break;
            default:
                mSignaturePad.setPenColor(Color.BLACK);
                break;
        }

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            result = bundle.getString("result");
        }

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Log.d("sun", "starting signature");
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                Log.d("sun", "on signed");
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
                Log.d("sun", "on clear");
            }
        });
    }

    public static void getActivity(Activity mAct) {
        mActivity = mAct;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DoneButton:
                //showInputDialog();
                savePhoto();
                Log.d("sun", file);
                if (!file.equals("")) {
                    Log.d("sun", "in");
                    Intent intent = new Intent();
                    intent.putExtra("result", file);
                    setResult(Activity.RESULT_OK, intent);
//			finish();
                    SignActivity.this.finish();
                }
                break;

            case R.id.ClearButton:

//        	customCanvas.clearCanvas();
                mSignaturePad.clear();
                break;

            case R.id.txt_black:

                mSignaturePad.setPenColor(Color.BLACK);
                break;

            case R.id.txt_red:

                mSignaturePad.setPenColor(Color.RED);
                break;

            case R.id.txt_blue:
                mSignaturePad.setPenColor(Color.BLUE);
                break;
            default:
                break;
        }

    }

    public void bm_forSave(View v) {
        bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas can = new Canvas(bitmap);
        v.draw(can);
    }

    private void savePhoto() {
        try {

//			bm_forSave(customCanvas);
            Bitmap bmp = mSignaturePad.getSignatureBitmap();

            String timemillis = System.currentTimeMillis() + ".png";
            String file_path = FileUtils.getBaseFilePath() + File.separator + timemillis;


            FileOutputStream out = new FileOutputStream(new File(file_path));
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", file_path);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
//			MediaStore.Images.Media.insertImage(getContentResolver(),filename.getAbsolutePath(), filename.getName(),filename.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "saved successfully", Toast.LENGTH_LONG).show();

    }
}
