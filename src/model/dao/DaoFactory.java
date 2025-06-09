package model.dao;

import db.DB;
import model.dao.impl.GameDaoJDBC;
import model.dao.impl.StudioDaoJDBC;

public class DaoFactory {
    public static StudioDao createStudioDao() {
        return new StudioDaoJDBC(DB.getConnection());
    }

    public static GameDao createGameDao() {
        return new GameDaoJDBC(DB.getConnection());
    }
}
