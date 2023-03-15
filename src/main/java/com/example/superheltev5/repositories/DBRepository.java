package com.example.superheltev5.repositories;

import com.example.superheltev5.dto.HeroCityDTO;
import com.example.superheltev5.dto.HeroDTO;
import com.example.superheltev5.dto.SuperpowerCountDTO;
import com.example.superheltev5.dto.SuperpowerDTO;
import com.example.superheltev5.models.Superpower;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("superhero_DB")
public class DBRepository implements IRepository{
    List<HeroDTO> heroes;
    List<SuperpowerDTO> heroSuperpowers;
    List<HeroCityDTO> heroCityList;
    List<SuperpowerCountDTO> superpowerCountList;

    // Getting superheroes by name search
    @Override
    public List<HeroDTO> getHeroesByHeroName(String heroName) {

        heroes = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT id, heroname, realname, creationdate FROM superhero WHERE heroname = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, heroName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("heroname");
                String realName = rs.getString("realname");
                LocalDate creationdate = rs.getDate("creationdate").toLocalDate();
                HeroDTO hero = new HeroDTO(id, name, realName, creationdate);
                heroes.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return heroes;
    }

    // Getting all heroes from superhero table
    @Override
    public List<HeroDTO> getAllHeroes() {
        heroes = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT id, heroname, realname, creationdate FROM superhero";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("heroname");
                String realName = rs.getString("realname");
                LocalDate creationdate = rs.getDate("creationdate").toLocalDate();
                HeroDTO hero = new HeroDTO(id, name, realName, creationdate);
                heroes.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return heroes;
    }

    // Getting superpowers and hero from heroname search
    @Override
    public List<SuperpowerDTO> getSuperpowersByHeroName(String heroName) {
        heroSuperpowers = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT superhero.heroname, superhero.realname, superpower.id, superpower.name FROM superhero " +
                    "JOIN superheropower ON superhero.id = superheropower.heroid " +
                    "JOIN superpower ON superheropower.superpowerid = superpower.id WHERE superhero.heroname = ? " +
                    "GROUP BY superhero.heroname, superhero.realname, superpower.id, superpower.name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, heroName);
            ResultSet rs = stmt.executeQuery();

            SuperpowerDTO hero = null;
            while (rs.next()) {
                String name = rs.getString("heroname");
                String realName = rs.getString("realname");
                String powerName = rs.getString("name");
                int powerID = rs.getInt("id");
                if (hero == null || !hero.getHeroName().equals(name)) {
                    if (hero != null) {
                        heroSuperpowers.add(hero);
                    }
                    hero = new SuperpowerDTO(name, realName);
                }
                hero.addSuperpower(new Superpower(powerID, powerName));
            }
            if (hero != null) {
                heroSuperpowers.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return heroSuperpowers;
    }

    // Getting all superheroes and their powers
    @Override
    public List<SuperpowerDTO> getSuperpowers() {
        heroSuperpowers = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT superhero.heroname, superhero.realname, superpower.id, superpower.name FROM superhero " +
                    "JOIN superheropower ON superhero.id = superheropower.heroid " +
                    "JOIN superpower ON superheropower.superpowerid = superpower.id " +
                    "GROUP BY superhero.heroname, superhero.realname, superpower.id, superpower.name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            SuperpowerDTO hero = null;
            while (rs.next()) {
                String name = rs.getString("heroname");
                String realName = rs.getString("realname");
                String powerName = rs.getString("name");
                int powerID = rs.getInt("id");
                if (hero == null || !hero.getHeroName().equals(name)) {
                    if (hero != null) {
                        heroSuperpowers.add(hero);
                    }
                    hero = new SuperpowerDTO(name, realName);
                }
                hero.addSuperpower(new Superpower(powerID, powerName));
            }
            if (hero != null) {
                heroSuperpowers.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return heroSuperpowers;
    }

    @Override
    public List<SuperpowerCountDTO> getSuperpowersCountByHeroName(String heroName) {
        superpowerCountList = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT superhero.heroname, COUNT(superheropower.superpowerid) AS superpowersCount " +
                    "FROM superhero " +
                    "JOIN superheropower ON superhero.id = superheropower.heroid WHERE superhero.heroname = ?" +
                    "GROUP BY superhero.heroname";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,heroName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("heroname");
                int superpowersCount = rs.getInt("superpowersCount");
                SuperpowerCountDTO hero = new SuperpowerCountDTO(name,superpowersCount);
                superpowerCountList.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return superpowerCountList;
    }

    @Override
    public List<SuperpowerCountDTO> getSuperpowersCount() {
        superpowerCountList = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT superhero.heroname, COUNT(superheropower.superpowerid) AS superpowersCount " +
                    "FROM superhero " +
                    "JOIN superheropower ON superhero.id = superheropower.heroid " +
                    "GROUP BY superhero.heroname";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("heroname");
                int superpowersCount = rs.getInt("superpowersCount");
                SuperpowerCountDTO hero = new SuperpowerCountDTO(name,superpowersCount);
                superpowerCountList.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return superpowerCountList;
    }

    // Getting the heroes and their respective city by heroname
    @Override
    public List<HeroCityDTO> getHeroesAndCityByHeroName(String cityName) {
        heroCityList = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT city.name, GROUP_CONCAT(superhero.heroname SEPARATOR ', ') AS heroes " +
                    "FROM superhero " +
                    "JOIN city ON superhero.cityid = city.cityid WHERE city.name = ?" +
                    "GROUP BY city.name " +
                    "ORDER BY city.name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cityName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("heroes");
                String cityNames = rs.getString("name");
                HeroCityDTO hero = new HeroCityDTO(name,cityNames);
                heroCityList.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return heroCityList;
    }

    // Getting all the heroes and their respective cities
    @Override
    public List<HeroCityDTO> getHeroesAndCity() {
        heroCityList = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT city.name, GROUP_CONCAT(superhero.heroname SEPARATOR ', ') AS heroes " +
                    "FROM superhero " +
                    "JOIN city ON superhero.cityid = city.cityid " +
                    "GROUP BY city.name " +
                    "ORDER BY city.name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("heroes");
                String cityName = rs.getString("name");
                HeroCityDTO hero = new HeroCityDTO(name,cityName);
                heroCityList.add(hero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return heroCityList;
    }
}
