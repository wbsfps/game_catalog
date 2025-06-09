package model.dao;

import model.entities.studio.Studio;

import java.util.List;

public interface StudioDao {

    void insert(Studio studio);
    void update(Studio studio);
    Studio findById(Integer id);
    void deleteById(Integer id);
    List<Studio> findAll();
}
