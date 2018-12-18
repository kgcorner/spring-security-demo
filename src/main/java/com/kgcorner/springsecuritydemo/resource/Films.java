package com.kgcorner.springsecuritydemo.resource;

import com.kgcorner.springsecuritydemo.data.Film;
import com.kgcorner.springsecuritydemo.service.FilmsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Films {

    @Autowired
    private FilmsServices filmsServices;

    @GetMapping("/api/films")
    public List<Film> getFilms() {
        return filmsServices.getAllFilms();
    }

    @PostMapping("/admin/films")
    @ResponseStatus(HttpStatus.CREATED)
    public String createFilm() {
        return "Film created";
    }
}
