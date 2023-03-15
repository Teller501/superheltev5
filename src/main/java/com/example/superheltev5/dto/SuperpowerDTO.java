package com.example.superheltev5.dto;

import com.example.superheltev5.models.Superpower;

import java.util.ArrayList;
import java.util.List;

public class SuperpowerDTO {
    private String heroName;
    private String realName;
    private List<Superpower> superpowers = new ArrayList<>();
    private int numberOfSuperpowers;

    public SuperpowerDTO(String heroName, String realName) {
        this.heroName = heroName;
        this.realName = realName;
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

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void addSuperpower(Superpower superpower) {
        this.superpowers.add(superpower);
    }
}
