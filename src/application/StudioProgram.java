package application;

import model.dao.DaoFactory;
import model.dao.StudioDao;
import model.entities.studio.Studio;

import java.util.List;

public class StudioProgram {
    public static void main(String[] args) {
        StudioDao studioDao = DaoFactory.createStudioDao();

        System.out.println("--- TEST 1 - insert studio ---");
        Studio newStudio = new Studio(null, "Epic Games", "USA");
        studioDao.insert(newStudio);
        System.out.println(newStudio);

        System.out.println("--- TEST 2 - findById ---");
        Studio studio = studioDao.findById(2);
        System.out.println(studio);

        System.out.println("--- TEST 3 - update studio ---");
        studio = studioDao.findById(16);
        studio.setName("Aquiris");
        studio.setCountry("BRA");
        studioDao.update(studio);
        System.out.println("Update Completed!");

        System.out.println("--- TEST 5 - findAll ---");
        List<Studio> studios = studioDao.findAll();
        studios.forEach(System.out::println);

        System.out.println("--- TEST 6 - deleteById ---");
        studioDao.deleteById(19);
        System.out.println("Delete Completed!");
    }
}
