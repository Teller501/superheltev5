package com.example.superheltev5.repositories;
import com.example.superheltev5.dto.*;
import com.example.superheltev5.models.City;
import com.example.superheltev5.models.Superhero;

import java.util.List;

public interface IRepository {
    List<HeroDTO> getHeroesByHeroName(String heroName);
    List<HeroDTO> getAllHeroes();
    List<SuperpowerDTO> getSuperpowersByHeroName(String heroName);
    List<SuperpowerDTO> getSuperpowers();

    List<SuperpowerCountDTO> getSuperpowersCountByHeroName(String heroName);
    List<SuperpowerCountDTO> getSuperpowersCount();
    List<HeroCityDTO> getHeroesAndCityByHeroName(String cityName);
    List<HeroCityDTO> getHeroesAndCity();
    public List<String> getCities();
    public List<String> getPowers();

    public void addSuperHero(SuperheroFormDTO form);

    public void deleteProductById(int id);

    SuperheroFormDTO findSuperheroById(int id);

    City findCityById(int id);

    List<String> findPowersByHeroId(int id);

    void updateHero(SuperheroFormDTO hero);

}
