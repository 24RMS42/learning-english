package timer.fityfor.me.beginner.domains;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Misha on 24.09.2017.
 */
@DatabaseTable(tableName = "animal")
public class Animal implements Serializable {

    @DatabaseField(generatedId = true, columnName = "_id")
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "name_pers")
    private String namePers;
    @DatabaseField(columnName = "category_id")
    private String categoryId;
    @DatabaseField(columnName = "is_cartoon")
    private boolean isCartoon;
    @DatabaseField(columnName = "is_sound")
    private boolean isSound;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNamePers() {
        return namePers;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public boolean isCartoon() {
        return isCartoon;
    }

    public boolean isSound() {
        return isSound;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamePers(String namePers) {
        this.namePers = namePers;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCartoon(boolean cartoon) {
        isCartoon = cartoon;
    }

    public void setSound(boolean sound) {
        isSound = sound;
    }
}