DROP DATABASE IF EXISTS superheroes;
CREATE DATABASE superheroes;

USE superheroes;

CREATE TABLE city(
                     name varchar(100),
                     cityid int NOT NULL,
                     PRIMARY KEY (cityid),
                     UNIQUE INDEX name_UNIQUE (name)
);

CREATE TABLE superpower(
                           id int NOT NULL AUTO_INCREMENT,
                           name varchar(50),
                           PRIMARY KEY (id)
);

CREATE TABLE superhero (
                           id int NOT NULL AUTO_INCREMENT,
                           heroname varchar(50),
                           realname varchar(50),
                           creationdate date,
                           humanstatus boolean,
                           cityid int NOT NULL,
                           superpowerid int,
                           PRIMARY KEY (id),
                           FOREIGN KEY (cityid) REFERENCES city(cityid),
                           FOREIGN KEY (superpowerid) REFERENCES superpower(id),
                           UNIQUE INDEX heroname_UNIQUE (heroname)
);

CREATE TABLE superheropower (
                                id int NOT NULL auto_increment,
                                heroid int,
                                superpowerid int,
                                PRIMARY KEY (id),
                                FOREIGN KEY (heroid) REFERENCES superhero(id) ON DELETE CASCADE,
                                FOREIGN KEY (superpowerid) REFERENCES superpower(id) ON DELETE CASCADE
);