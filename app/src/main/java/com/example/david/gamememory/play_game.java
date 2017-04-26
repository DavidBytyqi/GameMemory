package com.example.david.gamememory;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.Random;
import java.util.concurrent.RunnableFuture;

public class play_game extends AppCompatActivity implements View.OnClickListener {
    private int numberOfElements;
    private Button[] buttons;
    private int[] buttonGraphicLocations;
    private int[] buttonGraphics;

    private MemoryButton selecedButton1;
    private MemoryButton selecedButton2;
    private boolean isBusy = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout_memory);

        int numColumns = gridLayout.getColumnCount();
        int numRows = gridLayout.getRowCount();

        numberOfElements = numColumns * numRows;
        buttons = new MemoryButton[numberOfElements];
        buttonGraphics=new int[numberOfElements/2];
        buttonGraphicLocations[0] = R.drawable.btn1;
        buttonGraphicLocations[1] = R.drawable.btn2;
        buttonGraphicLocations[2] = R.drawable.btn3;
        buttonGraphicLocations[3] = R.drawable.btn4;
        buttonGraphicLocations[4] = R.drawable.btn5;
        buttonGraphicLocations[5] = R.drawable.btn6;
        buttonGraphicLocations[6] = R.drawable.btn7;
        buttonGraphicLocations[7] = R.drawable.btn8;

        buttonGraphicLocations = new int[numberOfElements];

        shuffleButtonGraphics();

        for (int r = 0; r < numRows; r++) {
            for(int c=0;c<numColumns;c++) {
                MemoryButton tempButton = new MemoryButton(this, r, c, buttonGraphics[buttonGraphicLocations[r * numColumns + c]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[r * numColumns + c] = tempButton;
                gridLayout.addView(tempButton);
            }
        }
    }

    protected void shuffleButtonGraphics() {
        Random rand = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            int temp = buttonGraphicLocations[i];
            int swapIndex = rand.nextInt(16);
            buttonGraphicLocations[i] = buttonGraphicLocations[swapIndex];
            buttonGraphicLocations[swapIndex] = temp;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if (isBusy) {
            return;
        }
        MemoryButton button = (MemoryButton)v;
        if (button.isMached) {
            return;
        }
        if (selecedButton1 == null) {
            selecedButton1 = button;
            selecedButton1.flip();
            return;
        }
        if (selecedButton1.getId() == button.getId()) {

            return;
        }
        if (selecedButton1.getFrontDrawableId() == button.getFrontDrawableId()) {

            button.flip();
            button.setMached(true);
            selecedButton1.setEnabled(false);
            selecedButton2.setEnabled(false);

            selecedButton1 = null;
            return;
        } else {
            selecedButton2 = button;
            selecedButton2.flip();
            isBusy = true;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    selecedButton2.flip();
                    selecedButton1.flip();
                    selecedButton1 = null;
                    selecedButton2 = null;
                    isBusy = false;
                }
            }, 500);
        }
    }
}