DROP TABLE IF EXISTS sport CASCADE;
CREATE TABLE sport (
    id serial PRIMARY KEY,
    name text NOT NULL
);

DROP TABLE IF EXISTS coach CASCADE;
CREATE TABLE coach (
    id serial PRIMARY KEY,
    name text NOT NULL
);

DROP TABLE IF EXISTS coach_sport CASCADE;
CREATE TABLE coach_sport (
coach_id int REFERENCES coach(id) NOT NULL,
sport_id int REFERENCES sport(id) NOT NULL,
PRIMARY KEY(coach_id, sport_id)
);

DROP TABLE IF EXISTS club CASCADE;
CREATE TABLE club (
    id serial PRIMARY KEY,
    name text NOT NULL
);

DROP TABLE IF EXISTS sportsman CASCADE;
CREATE TABLE sportsman (
    id serial PRIMARY KEY,
    name text NOT NULL,
    club_id int REFERENCES club(id)
);

DROP TABLE IF EXISTS coach_sportsman CASCADE;
CREATE TABLE coach_sportsman (
    coach_id int REFERENCES coach(id),
    sportsman_id int REFERENCES sport(id),
    PRIMARY KEY(coach_id, sportsman_id)
);

DROP TABLE IF EXISTS sport_achievement CASCADE;
CREATE TABLE sport_achievement (
    id serial PRIMARY KEY,
    sportsman_id int REFERENCES sportsman(id) NOT NULL,
    name text NOT NULL,
    discharge int,
    coach_id int REFERENCES coach(id)
);

DROP TABLE IF EXISTS place CASCADE;
CREATE TABLE place (
                       id int NOT NULL,
                       type_id int NOT NULL,
                       PRIMARY KEY (id, type_id),
                       name text,
                       address text
);

DROP TABLE IF EXISTS stadium CASCADE;
CREATE TABLE stadium (
                         id serial,
                         type_id int DEFAULT 3,
                         is_covered boolean NOT NULL,
                         places_count int NOT NULL,
                         square numeric(5,2) NOT NULL,
                         PRIMARY KEY (id, type_id),
                         CHECK (type_id = 3),
                         FOREIGN KEY (id, type_id) REFERENCES place (id, type_id)
);

DROP TABLE IF EXISTS pool CASCADE;
CREATE TABLE pool (
                      id int NOT NULL UNIQUE,
                      type_id int DEFAULT 1,
                      length int NOT NULL,
                      depth int NOT NULL,
                      lanes_count int DEFAULT 1,
                      PRIMARY KEY (id, type_id),
                      FOREIGN KEY (id, type_id) REFERENCES place(id, type_id),
                      CHECK (type_id = 1)
);

DROP TABLE IF EXISTS gym CASCADE;
CREATE TABLE gym (
                     id serial,
                     type_id int DEFAULT 2,
                     square numeric(5,2) NOT NULL,
                     PRIMARY KEY (id, type_id),
                     FOREIGN KEY (id, type_id) REFERENCES place(id, type_id),
                     CHECK (type_id = 2)
);

DROP TABLE IF EXISTS competition CASCADE;
CREATE TABLE competition (
                             id serial PRIMARY KEY,
                             place_id int,
                             place_type_id int,
                             FOREIGN KEY (place_id, place_type_id) REFERENCES place(id, type_id),
                             sport_id int REFERENCES sport(id) NOT NULL,
                             fst_id int REFERENCES sportsman(id) NOT NULL,
                             snd_id int REFERENCES sportsman(id),
                             thd_id int REFERENCES sportsman(id)
);
