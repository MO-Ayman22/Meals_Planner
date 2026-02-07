package com.example.mealsplanner.data.source.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealsplanner.data.model.entity.AreaEntity;
import com.example.mealsplanner.data.model.entity.CategoryEntity;
import com.example.mealsplanner.data.model.entity.FavoriteMealEntity;
import com.example.mealsplanner.data.model.entity.PlannedMealEntity;
import com.example.mealsplanner.data.model.entity.UserEntity;
import com.example.mealsplanner.data.source.local.dao.AreaDao;
import com.example.mealsplanner.data.source.local.dao.CategoryDao;
import com.example.mealsplanner.data.source.local.dao.FavoriteMealDao;
import com.example.mealsplanner.data.source.local.dao.PlannedMealDao;
import com.example.mealsplanner.data.source.local.dao.UserDao;

@Database(
        entities = {UserEntity.class, AreaEntity.class, CategoryEntity.class,
                FavoriteMealEntity.class, PlannedMealEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "meals_planner_db";
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao getUserDAO();

    public abstract AreaDao getAreaDAO();

    public abstract CategoryDao getCategoryDAO();

    public abstract FavoriteMealDao getFavoriteMealDAO();

    public abstract PlannedMealDao getPlannedMealDAO();


    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "DATABASE_NAME"
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
