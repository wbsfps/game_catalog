package model.dao;

import model.entities.game.Game;
import model.entities.studio.Studio;

import java.util.List;

public interface GameDao {

    void insert(Game game);
    void update(Game game);
    void deleteById(Integer id);
    Game findByid(Integer id);
    List<Game> findAll();

    List<Game> findByStudio(Studio studio);
}
