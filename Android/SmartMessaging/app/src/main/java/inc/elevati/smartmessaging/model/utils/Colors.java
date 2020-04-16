package inc.elevati.smartmessaging.model.utils;

import inc.elevati.smartmessaging.R;

public class Colors {

    public static int getColorByPriority(int priority) {
        switch (priority) {
            case 1:
                return R.attr.colorPriority1;
            case 2:
                return R.attr.colorPriority2;
            case 3:
                return R.attr.colorPriority3;
            case 4:
                return R.attr.colorPriority4;
            case 5:
                return R.attr.colorPriority5;
        }
        return R.attr.colorPriority1;
    }
}
