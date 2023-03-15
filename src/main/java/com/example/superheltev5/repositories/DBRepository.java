package com.example.superheltev5.repositories;

import com.example.superheltev5.dto.*;
import com.example.superheltev5.models.Superpower;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    public void addSuperHero(SuperheroFormDTO form) {
        try (Connection con = DBManager.getConnection()) {
// ID's
            int cityId = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();

// find city_id
            String SQL1 = "select cityid from city where name = ?;";

            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, form.getCity());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cityId = rs.getInt("cityid");
            }

// insert row in superhero table
            String SQL2 = "insert into superhero (heroname, realname, creationdate, cityid) " +
                    "values(?, ?, ?, ?);";

            pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); // return autoincremented key
            pstmt.setString(1, form.getHeroName());
            pstmt.setString(2, form.getRealName());
            pstmt.setDate(3, Date.valueOf(form.getCreationDate()));
            pstmt.setInt(4, cityId);

            int rows = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                heroId = rs.getInt(1);
            }


            String SQL3 = "select id from superpower where name = ?;";
            pstmt = con.prepareStatement(SQL3);

            for (String power : form.getPowerList()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("id"));
                }
            }

            // insert entries in superhero_powers join table

            String SQL4 = "insert into superheropower (heroid, superpowerid) values (?, ?);";
            pstmt = con.prepareStatement(SQL4);

            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId); // set the value of heroid
                pstmt.setInt(2, powerIDs.get(i)); // set the value of superpowerid
                pstmt.executeUpdate();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> getCities(){
        List<String> cities = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT name FROM city";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                cities.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    public List<String> getPowers(){
        List<String> powers = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT name FROM superpower";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                powers.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return powers;
    }
}
