CREATE TABLE IF NOT EXISTS sport (
    id serial PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE IF NOT EXISTS coach (
    id serial PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE IF NOT EXISTS coach_sport (
coach_id int REFERENCES coach(id) NOT NULL,
sport_id int REFERENCES sport(id) NOT NULL,
PRIMARY KEY(coach_id, sport_id)
);

CREATE TABLE IF NOT EXISTS club (
    id serial PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE IF NOT EXISTS sportsman (
    id serial PRIMARY KEY,
    name text NOT NULL,
    club_id int REFERENCES club(id)
);

CREATE TABLE IF NOT EXISTS coach_sportsman (
    coach_id int REFERENCES coach(id),
    sportsman_id int REFERENCES sport(id),
    PRIMARY KEY(coach_id, sportsman_id)
);

CREATE TABLE IF NOT EXISTS sport_achievement (
    id serial PRIMARY KEY,
    sportsman_id int REFERENCES sportsman(id) NOT NULL,
    name text NOT NULL,
    discharge int,
    coach_id int REFERENCES coach(id)
);

CREATE TABLE IF NOT EXISTS place (
                       id int NOT NULL,
                       type_id int NOT NULL,
                       PRIMARY KEY (id, type_id),
                       name text,
                       address text
);

CREATE TABLE IF NOT EXISTS stadium (
                         id serial,
                         type_id int DEFAULT 3,
                         is_covered boolean NOT NULL,
                         places_count int NOT NULL,
                         square numeric(5,2) NOT NULL,
                         PRIMARY KEY (id, type_id),
                         CHECK (type_id = 3),
                         FOREIGN KEY (id, type_id) REFERENCES place (id, type_id)
);

CREATE TABLE IF NOT EXISTS pool (
                      id int NOT NULL UNIQUE,
                      type_id int DEFAULT 1,
                      length int NOT NULL,
                      depth int NOT NULL,
                      lanes_count int DEFAULT 1,
                      PRIMARY KEY (id, type_id),
                      FOREIGN KEY (id, type_id) REFERENCES place(id, type_id),
                      CHECK (type_id = 1)
);

CREATE TABLE IF NOT EXISTS gym (
                     id serial,
                     type_id int DEFAULT 2,
                     square numeric(5,2) NOT NULL,
                     PRIMARY KEY (id, type_id),
                     FOREIGN KEY (id, type_id) REFERENCES place(id, type_id),
                     CHECK (type_id = 2)
);

CREATE TABLE IF NOT EXISTS competition (
                             id serial PRIMARY KEY,
                             place_id int,
                             place_type_id int,
                             FOREIGN KEY (place_id, place_type_id) REFERENCES place(id, type_id),
                             sport_id int REFERENCES sport(id) NOT NULL,
                             fst_id int REFERENCES sportsman(id) NOT NULL,
                             snd_id int REFERENCES sportsman(id),
                             thd_id int REFERENCES sportsman(id)
);
