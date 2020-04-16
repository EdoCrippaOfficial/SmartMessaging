package inc.elevati.smartmessaging.utils;

import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;

import androidx.core.content.ContextCompat;
import inc.elevati.smartmessaging.R;

public class Utils {

    public static void setBackgroundColor(View background, int priority) {
        int priorityColorID = R.attr.colorPriority1;
        switch (priority) {
            case 1:
                priorityColorID = R.attr.colorPriority1;
                break;
            case 2:
                priorityColorID = R.attr.colorPriority2;
                break;
            case 3:
                priorityColorID = R.attr.colorPriority3;
                break;
            case 4:
                priorityColorID = R.attr.colorPriority4;
                break;
            case 5:
                priorityColorID = R.attr.colorPriority5;
                break;
        }
        int[] attrs = {R.attr.colorPrimary, priorityColorID};
        TypedArray typedArray = background.getContext().obtainStyledAttributes(attrs);
        int primaryColor = typedArray.getColor(0, ContextCompat.getColor(background.getContext(), R.color.colorPrimaryLight));
        int priorityColor = typedArray.getColor(1, ContextCompat.getColor(background.getContext(), R.color.colorPriorityLight1));
        typedArray.recycle();
        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setColor(primaryColor);
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setColor(priorityColor);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
        background.setBackground(stateListDrawable);
    }
}
