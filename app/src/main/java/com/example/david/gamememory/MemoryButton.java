package com.example.david.gamememory;

/**
 * Created by david on btn4/26/2017.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;
import android.widget.GridLayout;

/**
 * Created by david on btn4/26/2017.
 */

public class MemoryButton  extends Button {

    protected int row;
    protected int column;
    protected int frontDrawableId;
    protected Drawable front;
    protected Drawable back;
    protected boolean isFlipped=false;
    protected boolean isMached=false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MemoryButton(Context context, int r, int c, int frontImageDrawableId){
        super(context);
        row=r;
        column=c;
        frontDrawableId=frontImageDrawableId;
        front= AppCompatDrawableManager.get().getDrawable(context, frontImageDrawableId);
        back=AppCompatDrawableManager.get().getDrawable(context, R.drawable.question);
        setBackground(back);
        GridLayout.LayoutParams tempParams=new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
        tempParams.width=(int)getResources().getDisplayMetrics().density*50;
        tempParams.height=(int)getResources().getDisplayMetrics().density*50;
        setLayoutParams(tempParams);

    }

    public boolean isMached() {
        return isMached;
    }

    public void setMached(boolean mached) {
        isMached = mached;
    }

    public int getFrontDrawableId() {
        return frontDrawableId;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void flip(){
        if(isMached){
            return;
        }
        if(isFlipped){
            setBackground(back);
            isFlipped=false;
        }
        else{
            setBackground(front);
            isFlipped=true;
        }

    }
}