package com.example.customview.utils;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by to-explore-future on 2017/first/18 20:06
 */
public class DrawTextUtils {

    public static void drawText(Canvas canvas, String text, float x, float y, Paint paintCircle) {
        char texts[] = text.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < texts.length / 25 + 1; i++) {
            for (int j = 0; j < 25; j++) {
                if ((j + i * 25) >= texts.length) {
                    break;
                } else {
                    buffer.append(texts[j + i * 25]);
                }
            }
            String everyLineText = buffer.toString();
            canvas.drawText(everyLineText, x, y + i * 50, paintCircle);
            buffer.delete(0, buffer.length());
        }
    }
}
