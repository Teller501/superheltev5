package com.example.superheltev5.controllers;

import com.example.superheltev5.models.Superpower;
import com.example.superheltev5.repositories.*;
import com.example.superheltev5.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("superhero")
public class SuperheroController {

    IRepository repository;

    public SuperheroController(ApplicationContext context, @Value("${superhero.repository.impl}") String impl){
        repository = (IRepository) context.getBean(impl);
    }

    @GetMapping("")
    public String getAllHeroes(Model model){
        List<HeroDTO> getAllHeroes = repository.getAllHeroes();

        model.addAttribute("heroes", getAllHeroes);

        return "index";
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<HeroDTO>> getHeroesByHeroName(@PathVariable String name){
        List<HeroDTO> searchResults = repository.getHeroesByHeroName(name);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/superpower/count/{name}")
    public ResponseEntity<List<SuperpowerCountDTO>> getSuperpowersCountByHeroName(@PathVariable String name) {
        List<SuperpowerCountDTO> superpowers = repository.getSuperpowersCountByHeroName(name);
        return new ResponseEntity<>(superpowers, HttpStatus.OK);
    }

    @GetMapping("/superpower/count")
    public ResponseEntity<List<SuperpowerCountDTO>> getSuperpowersCountByHeroName() {
        List<SuperpowerCountDTO> superpowers = repository.getSuperpowersCount();
        return new ResponseEntity<>(superpowers, HttpStatus.OK);
    }

    @GetMapping("/superpower/{name}")
    public String getSuperpowersByHeroName(@PathVariable String name, Model model) {
        List<SuperpowerDTO> superpowers = repository.getSuperpowersByHeroName(name);
        model.addAttribute("superpowers", superpowers);
        for (SuperpowerDTO superpower : superpowers) {
            model.addAttribute("powers", superpower.getSuperpowers());
        }
        return "powers";
    }

    @GetMapping("/superpower")
    public ResponseEntity<List<SuperpowerDTO>> getSuperpowersByHeroName() {
        List<SuperpowerDTO> superpowers = repository.getSuperpowers();
        return new ResponseEntity<>(superpowers, HttpStatus.OK);
    }

    @GetMapping("/city/{name}")
    public ResponseEntity<List<HeroCityDTO>> getCityByHeroName(@PathVariable String name) {
        List<HeroCityDTO> heroesAndCities = repository.getHeroesAndCityByHeroName(name);
        return new ResponseEntity<>(heroesAndCities, HttpStatus.OK);
    }

    @GetMapping("/city")
    public ResponseEntity<List<HeroCityDTO>> getCityByHeroName() {
        List<HeroCityDTO> heroesAndCities = repository.getHeroesAndCity();
        return new ResponseEntity<>(heroesAndCities, HttpStatus.OK);
    }

    @GetMapping("/add")
    public String showCreateHero(Model model){
        SuperheroFormDTO hero = new SuperheroFormDTO();
        model.addAttribute("hero", hero);
        model.addAttribute("cities", repository.getCities());
        model.addAttribute("powers", repository.getPowers());
        return "add";
    }

    @PostMapping("/add")
    public String addHero(@ModelAttribute("hero") SuperheroFormDTO superheroFormDTO){
        repository.addSuperHero(superheroFormDTO);
        System.out.println("added hero: " + superheroFormDTO.toString());
        return "redirect:/superhero";
    }
}
