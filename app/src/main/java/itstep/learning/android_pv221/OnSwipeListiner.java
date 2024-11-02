package itstep.learning.android_pv221;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OnSwipeListiner implements View.OnTouchListener {
    private  final GestureDetector  gestureDetector;

    public OnSwipeListiner(Context context) {
        this.gestureDetector = new GestureDetector(context,new SwipeGestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    public void OnSwipeBottom(){}
    public void OnSwipeLeft(){}
    public void OnSwipeRight(){}
    public void OnSwipeTop(){}
    private final  class  SwipeGestureListener
            extends GestureDetector.SimpleOnGestureListener{
        private final static int minVeloncity = 150;
        private final static int minDistance = 100;
        private final static double minRatio = 1.0/2.0;
        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            boolean isHadled = false;
            if(e1==null) return  false;
            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();
            float distanceX = Math.abs(deltaY);
            float distanceY = Math.abs(deltaX);
            if(distanceX * minRatio > distanceY && distanceX >= minDistance){
                if(Math.abs(velocityX)>= minVeloncity){
                    if(deltaX>0){
                        OnSwipeRight();
                    }
                    else{
                        OnSwipeLeft();
                    }
                }
            }
            else if(distanceY * minRatio > distanceX && distanceY >= minDistance){
                if(Math.abs(velocityY) >= minVeloncity){
                    if(deltaY>0){
                        OnSwipeBottom();
                    }
                    else{
                        OnSwipeTop();
                    }
                }
            }
            return isHadled;
        }
    }
}
