package com.kgcorner.springsecuritydemo.service;

import com.kgcorner.springsecuritydemo.data.DataRepository;
import com.kgcorner.springsecuritydemo.data.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmsServices {

    @Autowired
    private DataRepository<Film> filmDataRepository;

    public List<Film> getAllFilms() {
        return filmDataRepository.getAll(Film.class);
    }

}
