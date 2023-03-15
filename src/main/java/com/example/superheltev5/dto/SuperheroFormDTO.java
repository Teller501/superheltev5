package com.example.superheltev5.dto;

import java.time.LocalDate;
import java.util.List;

public class SuperheroFormDTO {
    private int heroId;

    private String heroName;

    private String realName;

    private LocalDate creationDate;

    private String city;

    List<String> powerList;

    public SuperheroFormDTO(int heroId, String heroName, String realName,
                            LocalDate creationDate, String city, List<String> powerList) {
        this.heroId = heroId;
        this.heroName = heroName;
        this.realName = realName;
        this.creationDate = creationDate;
        this.city = city;
        this.powerList = powerList;
    }

    public SuperheroFormDTO() { // default konstruktør skal laves
    }

    public void add(String power) {
        powerList.add(power);
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getPowerList() {
        return powerList;
    }

    public void setPowerList(List<String> powerList) {
        this.powerList = powerList;
    }
}
