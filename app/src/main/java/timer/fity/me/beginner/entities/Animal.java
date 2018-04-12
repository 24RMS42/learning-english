package timer.fity.me.beginner.entities;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import timer.fity.me.beginner.interfacies.Constants;
import timer.fity.me.beginner.utils.GeneralMetods;

/**
 * Created by Hovhannisyan.Karo on 25.08.2017.
 */

public class Animal {
    private String eName;
    private String pName;
    private Uri eVoice;
    private Uri pVoice;
    private Drawable cImage;
    private Drawable aImage;
    private Uri animalVoice;
    private boolean isCartoon;
    private boolean isSound;

    public Animal() {
    }

    public Animal(String eName, String pName, Uri eVoice, Uri pVoice, Drawable cImage, Drawable aImage, Uri animalVoice) {
        this.eName = eName;
        this.pName = pName;
        this.eVoice = eVoice;
        this.pVoice = pVoice;
        this.cImage = cImage;
        this.aImage = aImage;
        this.animalVoice = animalVoice;
    }

    public boolean isCartoon() {
        return isCartoon;
    }

    public void setCartoon(boolean cartoon) {
        isCartoon = cartoon;
    }

    public boolean isSound() {
        return isSound;
    }

    public void setSound(boolean sound) {
        isSound = sound;
    }

    public String geteName() {
        return eName;
    }

    public String getpName() {
        return pName;
    }

    public Uri geteVoice() {
        return eVoice;
    }

    public Uri getpVoice() {
        return pVoice;
    }

    public Drawable getImageFromImageFolder(boolean isCartoon) {
        if (isCartoon) {
            return GeneralMetods.getSingleImageFromImagesFolder(Constants.KEYS.C + eName).get();
        } else {
            return GeneralMetods.getSingleImageFromImagesFolder(Constants.KEYS.RI + eName).get();
        }
    }

    public String getImageFromServer(boolean isCartoon) {
        if (isCartoon) {
            return "http://promoletter.com/learnenglish/images/" + Constants.KEYS.C + eName.toLowerCase() + ".png";
        } else {
            return "http://promoletter.com/learnenglish/images/" + Constants.KEYS.RI + eName.toLowerCase() + ".png";
        }
    }

    public Drawable getImageFromDrawable() {
        return GeneralMetods.getSingleImageFromDrawable(Constants.KEYS.RI + eName).get();
    }

    public Uri getAnimalVoice() {
        return animalVoice;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "eName='" + eName + '\'' +
                ", pName='" + pName + '\'' +
                ", eVoice=" + eVoice +
                ", pVoice=" + pVoice +
                ", cImage=" + cImage +
                ", aImage=" + aImage +
                ", animalVoice=" + animalVoice +
                '}';
    }
}
