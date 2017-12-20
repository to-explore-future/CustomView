package com.example.customview.activity.QRcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.customview.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class QRCodeActivity extends Activity implements View.OnClickListener {
    private static final int QRCODEWIDTH = 300;
    private static final int QRCODEHEIGHT = 300;
    private ImageView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Button displayQRCode = (Button) findViewById(R.id.btn_display_QRcode);
        qrCode = (ImageView) findViewById(R.id.iv_QRcode);
        displayQRCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_display_QRcode:
                generateQRcode();
        }
    }

    private void generateQRcode() {
        Bitmap bitmap = createQrCode("我是中国人");
        qrCode.setImageBitmap(bitmap);
    }

    public Bitmap createQrCode(String str) {
        Bitmap bitmap = null;
        try {
            String newStr = new String(str.getBytes(), "ISO-8859-1");
            Hashtable<EncodeHintType, String> hashtable = new Hashtable<>();
            hashtable.put(EncodeHintType.CHARACTER_SET, "ISO-8859-first");
            BitMatrix bitMatrix = new QRCodeWriter().encode(newStr, BarcodeFormat.QR_CODE, QRCODEWIDTH, QRCODEHEIGHT, hashtable);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[y * width + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
