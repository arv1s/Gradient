package com.sample.gradient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
//import android.os.Handler;
import android.util.DisplayMetrics;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConstraintLayout cLayout = findViewById(R.id.cLayout);
        cLayout.setBackgroundColor(Color.parseColor("#ffffff"));

        final TextView hexCode = findViewById(R.id.hexTextView);
        final TextView position = findViewById(R.id.positionText);
        position.setText(R.string.coordinatesText);
//        position.setTextColor(Color.parseColor("#ffffff"));

//        final Handler mHandler = new Handler();
        final View blankView = findViewById(R.id.blankView);
        blankView.setOnTouchListener(new View.OnTouchListener() {

            int distance, one=1;

            @Override
            public boolean onTouch(View v, final MotionEvent event) {

//                Runnable mAction = new Runnable() {
//                    DisplayMetrics displayMetrics = new DisplayMetrics();
//                    int width, height;
//                    int posX, posY;
//                    int ratioX, ratioY;
//                    @Override
//                    public void run() {
//                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                        width = displayMetrics.widthPixels;
//                        height = displayMetrics.heightPixels;
//                        posX = (int) event.getX();
//                        posY = (int) event.getY();
//                        cLayout.setBackgroundColor(Color.parseColor("#aaaaaa"));
//                        position.setText(getString(R.string.touchCoordinates, posX, height-posY));
//                        mHandler.postDelayed(this, 1000);
//                    }
//                };

                DisplayMetrics displayMetrics = new DisplayMetrics();
                posXY pos = new posXY();
                String hexColor;
                int posX, posY;
                double width, height;
                double ratioX, ratioY;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
//                        mHandler.post(mAction);
                        // Retrieve the dimensions to use with rationX and ratioY
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        width = displayMetrics.widthPixels;
                        height = displayMetrics.heightPixels;
                        // Validation and Assignment of press coordinates
                        pos.setXY((int) event.getX(), (int) event.getY());
                        posX = pos.getX();
                        posY = pos.getY();
                        // Assign the Hex value for Blue
                        distance += one;
                        if (distance <= 0 || distance >= 255) {
                            one *= -1;
                        }
                        // Ratio for mapping the press coordinates to a Hex Value
                        ratioX = posX / width;
                        ratioY = (height-posY) / height;
                        hexColor = getHex(ratioX, ratioY, distance);
                        // Update the Background color and the TextViews' values
                        cLayout.setBackgroundColor(Color.parseColor(hexColor));
                        position.setText(getString(R.string.touchCoordinates, posX, (int) height-posY));
//                        position.setTextColor(Color.parseColor(hexColor));
                        hexCode.setText(hexColor);
                        break;
                    case MotionEvent.ACTION_UP:
//                        mHandler.removeCallbacks(mAction);
//                        Log.i("upFlag", "upFlag triggered");
//                        position.setText(R.string.coordinatesText);
//                        cLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                        blankView.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }

        });

    }

    public String getHex(double ratioX, double ratioY, int distance) {
        // Assign Hex Values as Strings
        String rValue = Double.toHexString((int) (255 * ratioX));//.replace("-", "");
        String gValue = Double.toHexString((int) (255 * ratioY)).replace("-", "");
        String bValue = Integer.toHexString(distance);
        // Formatting the Hex String
        if(rValue.length() < 8) rValue = rValue.substring(0, 4) + "0" + rValue.substring(4);
        if(gValue.length() < 8) gValue = gValue.substring(0, 4) + "0" + gValue.substring(4);
        if(bValue.length() < 2) bValue = "0" + bValue;
        // Pulling the desired Hex String
        rValue = rValue.substring(4, 6);
        gValue = gValue.substring(4, 6);
        return "#" + rValue + gValue + bValue;
    }

//    public String smoothHex() {
//        String rValue = Double.toHexString( (int) (255 * ratioX) );
//        String gValue = Double.toHexString( (int) (255 * ratioY) );
//        String bValue = Integer.toHexString(distance);
//        if(rValue.length() < 8) rValue = rValue.substring(0, 4) + "0" + rValue.substring(4);
//        if(gValue.length() < 8) gValue = gValue.substring(0, 4) + "0" + gValue.substring(4);
//        if(bValue.length() < 2) bValue = "0" + bValue;
//        rValue = rValue.substring(4, 6);
//        gValue = gValue.substring(4, 6);
//        Log.i("colorHex", "#" + rValue + gValue + bValue);
//        return "#" + rValue + gValue + bValue;
//    }

    public static class posXY {
        private int x, y;

        public void setXY(int newX, int newY) {
            // Set the minimum to 0
            this.x = Math.max(0, newX);
            this.y = Math.max(0, newY);
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }

    }

}