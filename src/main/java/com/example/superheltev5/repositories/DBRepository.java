package com.example.superheltev5.repositories;

import com.example.superheltev5.dto.*;
import com.example.superheltev5.models.City;
import com.example.superheltev5.models.Superhero;
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
// ID's
            int cityId = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();

// find city_id
            String SQL1 = "select cityid from city where name = ?;";

            PreparedStatement pstmt = conn.prepareStatement(SQL1);
            pstmt.setString(1, form.getCity());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cityId = rs.getInt("cityid");
            }

// insert row in superhero table
            String SQL2 = "insert into superhero (heroname, realname, creationdate, cityid) " +
                    "values(?, ?, ?, ?);";

            pstmt = conn.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); // return autoincremented key
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
            pstmt = conn.prepareStatement(SQL3);

            for (String power : form.getPowerList()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("id"));
                }
            }

            // insert entries in superhero_powers join table

            String SQL4 = "insert into superheropower (heroid, superpowerid) values (?, ?);";
            pstmt = conn.prepareStatement(SQL4);

            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId); // set the value of heroid
                pstmt.setInt(2, powerIDs.get(i)); // set the value of superpowerid
                pstmt.executeUpdate();

            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteProductById(int id) {
        try (Connection conn = DBManager.getInstance().getConnection()){
            String SQL = "DELETE FROM superhero WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SuperheroFormDTO findSuperheroById(int id) {
        SuperheroFormDTO superhero = null;
        try (Connection conn = DBManager.getInstance().getConnection()){
            String SQL = "SELECT * FROM superhero WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    superhero = new SuperheroFormDTO();
                    superhero.setHeroName(rs.getString("heroname"));
                    superhero.setRealName(rs.getString("realname"));
                    superhero.setCreationDate(rs.getDate("creationdate").toLocalDate());
                    int cityID = rs.getInt("cityid");
                    superhero.setCity(findCityById(cityID).getName());
                    superhero.setPowerList(findPowersByHeroId(id));
                    superhero.setHeroId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return superhero;
    }

    @Override
    public City findCityById(int id) {
        City city = null;
        try (Connection conn = DBManager.getInstance().getConnection()){
            String SQL = "SELECT * FROM city WHERE cityid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    city = new City();
                    city.setCityID(rs.getInt("cityid"));
                    city.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public List<String> findPowersByHeroId(int heroId) {
        List<String> powers = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().getConnection()){
            String SQL = "SELECT sp.name FROM superheropower shp JOIN superpower sp ON shp.superpowerid = sp.id WHERE shp.heroid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
                stmt.setInt(1, heroId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    powers.add(rs.getString("name"));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return powers;
    }

    @Override
    public void updateHero(SuperheroFormDTO hero) {
        try(Connection conn = DBManager.getInstance().getConnection()){

            int cityId = 0;
            List<Integer> powerIDs = new ArrayList<>();

// find city_id
            String SQL1 = "select cityid from city where name = ?;";

            PreparedStatement pstmt = conn.prepareStatement(SQL1);
            pstmt.setString(1, hero.getCity());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cityId = rs.getInt("cityid");
            }

            String SQL2 = "UPDATE superhero SET heroname = ?, realname = ?, creationdate = ?, cityid = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(SQL2);
                stmt.setString(1, hero.getHeroName());
                stmt.setString(2, hero.getRealName());
                stmt.setDate(3, Date.valueOf(hero.getCreationDate()));
                stmt.setInt(4, cityId);
                stmt.setInt(5, hero.getHeroId());
                stmt.executeUpdate();

            String SQL3 = "select id from superpower where name = ?;";
            pstmt = conn.prepareStatement(SQL3);

            for (String power : hero.getPowerList()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("id"));
                }
            }

            // update entries in superhero_powers join table

            String SQL4 = "INSERT INTO superheropower (heroid, superpowerid) VALUES (?, ?);";
            pstmt = conn.prepareStatement(SQL4);

            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, hero.getHeroId()); // set the value of heroid
                pstmt.setInt(2, powerIDs.get(i)); // set the value of superpowerid
                pstmt.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<String> getCities(){
        List<String> cities = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().getConnection()){
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
        try (Connection conn = DBManager.getInstance().getConnection()){
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
