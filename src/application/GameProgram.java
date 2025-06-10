package application;

import model.dao.DaoFactory;
import model.dao.GameDao;
import model.entities.enums.genre.Genre;
import model.entities.game.Game;
import model.entities.studio.Studio;

import java.util.List;

public class GameProgram {
    public static void main(String[] args) {
        GameDao gameDao = DaoFactory.createGameDao();

        System.out.println("--- Test 1 - insert game ---");
        Studio studio = new Studio(8, null, null);
        Game newGame = new Game(null, "WOW", Genre.ACTION, "Blizzard", 8.0, studio);
        gameDao.insert(newGame);
        System.out.println("Inserted! New id = " + newGame.getId());

        System.out.println("--- Test 2 - findbyId game ---");
        Game game = gameDao.findById(2);
        System.out.println(game);

        System.out.println("--- Test 3 - findbyStudio ---");
        List<Game> games = gameDao.findByStudio(studio);
        games.forEach(System.out::println);

        System.out.println("--- Test 4 - findbyAll ---");
        games = gameDao.findAll();
        games.forEach(System.out::println);

        System.out.println("--- Test 5 - update ---");
        studio = new Studio(4, null, null);
        game = gameDao.findById(3);
        game.setName("Sea Of Thieves");
        game.setRating(7.0);
        game.setGenre(Genre.ACTION);
        game.setPlatform("Steam");
        game.setStudio(studio);
        gameDao.update(game);
        System.out.println("Update Completed!");

//        System.out.println("--- Test 6 - Delete game ---");
//        gameDao.deleteById(9);
//        System.out.println("Delete completed!");
    }
}
