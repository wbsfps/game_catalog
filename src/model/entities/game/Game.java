package model.entities.game;

import model.entities.enums.genre.Genre;
import model.entities.studio.Studio;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Game implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Genre genre;
    private String platform;
    private Double rating;

    private Studio studio;

    public Game(){}
    public Game(Integer id, String name, Genre genre, String platform, Double rating, Studio studio) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.platform = platform;
        this.rating = rating;
        this.studio = studio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", platform='" + platform + '\'' +
                ", rating=" + rating +
                ", studio=" + studio +
                '}';
    }
}
