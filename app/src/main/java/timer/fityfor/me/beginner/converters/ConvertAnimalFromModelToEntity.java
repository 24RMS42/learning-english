package timer.fityfor.me.beginner.converters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import timer.fityfor.me.beginner.domains.Animal;
import timer.fityfor.me.beginner.utils.DatabaseHelper;

/**
 * Created by Misha on 25.09.2017.
 */

public class ConvertAnimalFromModelToEntity {

    public static ArrayList<timer.fityfor.me.beginner.entities.Animal> converter(int categoryId, Context cntx) {

        List<timer.fityfor.me.beginner.domains.Animal> animalListDomain = new ArrayList<>();
        ArrayList<timer.fityfor.me.beginner.entities.Animal> animalListEntity = new ArrayList<>();

        Dao<Animal, Integer> playsDao;
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(
                cntx.getApplicationContext(),
                DatabaseHelper.class);

        playsDao = databaseHelper.getAnimalDao();

        try {
            animalListDomain = playsDao.queryForEq("category_id", categoryId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Animal animal : animalListDomain) {

            String eName = animal.getName();
            String pName = animal.getNamePers();
            Uri eVoice = Uri.parse("android.resource://" + cntx.getPackageName() + "/raw/" + "e_" + animal.getName());
            Uri pVoice = Uri.parse("android.resource://" + cntx.getPackageName() + "/raw/" + "p_" + animal.getName());
            Drawable cImage = null;
            Drawable aImage = null;
            Uri animalVoice = Uri.parse("android.resource://" + cntx.getPackageName() + "/raw/" + "e_" + animal.getName());

            timer.fityfor.me.beginner.entities.Animal animalEntity = new timer.fityfor.me.beginner.entities.Animal(eName, pName, eVoice, pVoice, null, null, animalVoice);
            animalEntity.setCartoon(animal.isCartoon());
            animalEntity.setSound(animal.isSound());
            animalListEntity.add(animalEntity);
        }

        return animalListEntity;
    }
}