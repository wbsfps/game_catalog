package model.dao.impl;

import db.DB;
import db.DBException;
import model.dao.StudioDao;
import model.entities.studio.Studio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudioDaoJDBC implements StudioDao {

    private Connection connection;

    public StudioDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Studio studio) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    insert into studio(name, country)
                    values(?, ?);
                    """, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, studio.getName());
            statement.setString(2, studio.getCountry());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    studio.setId(id);
                }
                DB.closeResultSet(resultSet);
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Studio studio) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    update studio
                    set name = ?, country = ?
                    where id = ?;
                    """);

            statement.setString(1, studio.getName());
            statement.setString(2, studio.getCountry());
            statement.setInt(3, studio.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Studio findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM studio WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Studio studio = instantiateStudio(resultSet);
                return studio;
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
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    delete from studio where id = ?;
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
    public List<Studio> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("""
                   select studio.* from studio
                   """);

            resultSet = statement.executeQuery();

            List<Studio> studios = new ArrayList<>();
            while (resultSet.next()) {
                Studio studio = instantiateStudio(resultSet);
                studios.add(studio);
            }
            return studios;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private Studio instantiateStudio(ResultSet resultSet) throws SQLException {
        Studio studio = new Studio();
        studio.setId(resultSet.getInt("id"));
        studio.setName(resultSet.getString("name"));
        studio.setCountry(resultSet.getString("country"));
        return studio;
    }
}
