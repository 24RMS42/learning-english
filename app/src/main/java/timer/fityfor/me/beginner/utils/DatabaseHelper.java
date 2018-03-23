package timer.fityfor.me.beginner.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import timer.fityfor.me.beginner.domains.Animal;

/**
 * Created by Misha on 04.10.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "animal.db";
    public static final int DATABASE_VERSION = 1;

    private Context context;
    private Dao<Animal, Integer> animalDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

    }

    public Dao<Animal, Integer> getAnimalDao() {
        if (animalDao == null) {
            try {
                animalDao = getDao(Animal.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return animalDao;
    }
}