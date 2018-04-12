package timer.fity.me.beginner.interfacies;

/**
 * Created by Hovhannisyan.Karo on 23.08.2017.
 */

public interface Constants {

    String BACKGROUND_LEARN_BASE = "background_learn_";
    String BACKGROUND_NEW_CATEGORY = "bg_new_category";

    String BACKGROUND_LEARN_AQUATICS = "real_image_aquatics";
    String BACKGROUND_LEARN_NOT_AQUATICS = "real_image_not_aquatics";

    interface CATEGORY {
        int BIRDS = 1;
        int AQUATIC = 2;
        int WHILD = 3;
        int DOMESTIC = 4;
        int MUSICAL = 5;
        int VERBS = 6;
        int TRANSPORTATIONS = 7;
    }

    interface KEYS {
        String RI = "i_";
        String C = "c_i_";
    }

    interface SOUND_TYPES {
        int SOUND = 1;
        int E_SOUND = 2;
        int P_SOUND = 3;
    }

    interface LEVELS {
        int EASY = 1;
        int MIDDLE = 2;
        int HARD = 3;
    }

    interface G_TYPES {
        int TWO = 2;
        int THREE = 3;
        int FOUR = 4;
        int FIVE = 5;
        int SIX = 6;
    }
}