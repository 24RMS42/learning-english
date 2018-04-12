package timer.fity.me.beginner.controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import timer.fity.me.beginner.R;
import timer.fity.me.beginner.fragments.LearnFragment;
import timer.fity.me.beginner.fragments.LevelSelectionFragment;
import timer.fity.me.beginner.fragments.PlayFragment;

/**
 * Created by Hovhannisyan.Karo on 23.08.2017.
 */

public class ViewController {

    private static ViewController dataController = null;
    private Activity contex;
    private FragmentManager fragmentManager;
    private LearnFragment learnFragment;
    private LevelSelectionFragment levelSelectionFragment;
    private PlayFragment playFragment;

    private ViewController() {
    }

    public static ViewController getViewController() {
        if (dataController == null) {
            dataController = new ViewController();
        }
        return dataController;
    }

    public LevelSelectionFragment getLevelSelectionFragment() {
        return levelSelectionFragment;
    }

    public void setLevelSelectionFragment(LevelSelectionFragment levelSelectionFragment) {
        this.levelSelectionFragment = levelSelectionFragment;
    }

    public PlayFragment getPlayFragment() {
        return playFragment;
    }

    public void setPlayFragment(PlayFragment playFragment) {
        this.playFragment = playFragment;
    }

    public LearnFragment getLearnFragment() {
        return learnFragment;
    }

    public void setLearnFragment(LearnFragment learnFragment) {
        this.learnFragment = learnFragment;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public Activity getContex() {
        return contex;
    }

    public void setContex(Activity contex) {
        this.contex = contex;
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_main_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    public void removeAllFragments() {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    public void resetViewController() {
        dataController = null;
    }
}