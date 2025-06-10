package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.GameDao;
import model.entities.enums.genre.Genre;
import model.entities.game.Game;
import model.entities.studio.Studio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDaoJDBC implements GameDao {

    private Connection connection;
    public GameDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Game game) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    insert into game(name, genre, platform, rating, studio_id)
                    values(?, ?, ?, ?, ?);
                    """, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getName());
            statement.setString(2, String.valueOf((Genre) game.getGenre()));
            statement.setString(3, game.getPlatform());
            statement.setDouble(4, game.getRating());
            statement.setInt(5, game.getStudio().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    game.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DBException("Unexpected error! No rows affected.");
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Game game) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    update game
                    set name = ?, genre = ?, platform = ?, rating = ?, studio_id = ?
                    where id = ?;
                    """);

            statement.setString(1, game.getName());
            statement.setString(2, String.valueOf((Genre) game.getGenre()));
            statement.setString(3, game.getPlatform());
            statement.setDouble(4, game.getRating());
            statement.setInt(5, game.getStudio().getId());
            statement.setInt(6, game.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    delete from game where id = ?;
                    """);

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Game findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("""
                    select game.*, studio.name as studioName, studio.country
                    from game
                    inner join studio on game.studio_id = studio.id
                    where game.id = ?;
                    """);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Studio studio = instatiateStudio(resultSet);
                Game game = instatiateGame(resultSet, studio);
                return game;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Game> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("""
                    select game.*, studio.name as studioName, studio.country
                    from game
                    left join studio on game.studio_id = studio.id
                    order by name;
                    """);
            resultSet = statement.executeQuery();

            List<Game> games = new ArrayList<>();
            Map<Integer, Studio> map = new HashMap<>();

            while(resultSet.next()) {
                Studio studio = map.get(resultSet.getInt("studio_id"));

                if (studio == null) {
                    studio = instatiateStudio(resultSet);
                    map.put(resultSet.getInt("studio_id"), studio);
                }

                Game game = instatiateGame(resultSet, studio);
                games.add(game);
            }
            return games;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Game> findByStudio(Studio studio) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("""
                    select game.*, studio.name as studioName, studio.country
                    from game
                    inner join studio on game.studio_id = studio.id
                    where game.studio_id = ?
                    order by name;
                    """);
            statement.setInt(1, studio.getId());
            resultSet = statement.executeQuery();

            List<Game> games = new ArrayList<>();
            Map<Integer, Studio> map = new HashMap<>();

            while (resultSet.next()) {
                Studio studio2 = map.get(resultSet.getInt("studio_id"));

                if (studio2 == null) {
                    studio2 = instatiateStudio(resultSet);
                    map.put(resultSet.getInt("studio_id"), studio2);
                }

                Game game = instatiateGame(resultSet, studio2);
                games.add(game);
            }
            return games;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private Studio instatiateStudio(ResultSet resultSet) throws SQLException {
        var studio = new Studio();
        studio.setId(resultSet.getInt("id"));
        studio.setName(resultSet.getString("studioName"));
        studio.setCountry(resultSet.getString("country"));
        return studio;
    }

    private Game instatiateGame(ResultSet resultSet, Studio studio) throws SQLException {
        var game = new Game();

        game.setId(resultSet.getInt("id"));
        game.setName(resultSet.getString("name"));
        game.setGenre(Genre.valueOf(resultSet.getString("genre")));
        game.setPlatform(resultSet.getString("platform"));
        game.setRating(resultSet.getDouble("rating"));
        game.setStudio(studio);

        return game;
    }
}
