package model.dao.impl;

import model.dao.GameDao;
import model.entities.game.Game;
import model.entities.studio.Studio;

import java.sql.Connection;
import java.util.List;

public class GameDaoJDBC implements GameDao {

    private Connection connection;
    public GameDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Game game) {

    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Game findByid(Integer id) {
        return null;
    }

    @Override
    public List<Game> findAll() {
        return List.of();
    }

    @Override
    public List<Game> findByStudio(Studio studio) {
        return List.of();
    }
}
