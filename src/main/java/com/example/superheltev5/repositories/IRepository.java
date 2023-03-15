package com.example.superheltev5.repositories;
import com.example.superheltev5.dto.HeroCityDTO;
import com.example.superheltev5.dto.HeroDTO;
import com.example.superheltev5.dto.SuperpowerCountDTO;
import com.example.superheltev5.dto.SuperpowerDTO;

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

}
