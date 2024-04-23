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
                                     address text,
                                     stadium_is_covered boolean,
                                     stadium_places_count int,
                                     stadium_square numeric(5,2),
                                     pool_length int,
                                     pool_depth int,
                                     pool_lanes_count int,
                                     gym_square numeric(5,2)
);

CREATE OR REPLACE FUNCTION check_stadium_values()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.type_id = 3 THEN
        IF NEW.stadium_places_count <= 0 THEN
            RAISE EXCEPTION 'Для стадиона stadium_places_count должен быть больше 0';
        END IF;
        IF NEW.stadium_square <= 0 THEN
            RAISE EXCEPTION 'Для стадиона stadium_square должен быть больше 0';
        END IF;
        IF NEW.pool_length IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 3 нельзя указать значение pool_length';
        END IF;
        IF NEW.pool_depth IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 3 нельзя указать значение pool_depth';
        END IF;
        IF NEW.pool_lanes_count IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 3 нельзя указать значение pool_lanes_count';
        END IF;
        IF NEW.gym_square IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 3 нельзя указать значение gym_square';
        END IF;
    ELSEIF NEW.type_id = 1 THEN
        IF NEW.pool_lanes_count <= 0 THEN
            RAISE EXCEPTION 'Для бассейна lanes_count должен быть больше нуля';
        END IF;
        IF NEW.pool_depth <= 0 THEN
            RAISE EXCEPTION 'Для бассейна pool_depth должен быть больше нуля';
        END IF;
        IF NEW.pool_length <= 0 THEN
            RAISE EXCEPTION 'Для бассейна pool_length должен быть больше нуля';
        END IF;
        IF NEW.stadium_is_covered IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 1 нельзя указать значение stadium_is_covered';
        END IF;
        IF NEW.stadium_places_count IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 1 нельзя указать значение stadium_places_count';
        END IF;
        IF NEW.stadium_square IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 1 нельзя указать значение stadium_square';
        END IF;
        IF NEW.gym_square IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 1 нельзя указать значение gym_square';
        END IF;
    ELSEIF NEW.type_id = 2 THEN
        IF NEW.gym_square <= 0 THEN
            RAISE EXCEPTION 'Для зала gym_square должен быть больше 0';
        end if;
        IF NEW.stadium_is_covered IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 2 нельзя указать значение stadium_is_covered';
        END IF;
        IF NEW.stadium_places_count IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 2 нельзя указать значение stadium_places_count';
        END IF;
        IF NEW.stadium_square IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 2 нельзя указать значение stadium_square';
        END IF;
        IF NEW.pool_length IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 2 нельзя указать значение pool_length';
        END IF;
        IF NEW.pool_depth IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 2 нельзя указать значение pool_depth';
        END IF;
        IF NEW.pool_lanes_count IS NOT NULL THEN
            RAISE EXCEPTION 'Для type_id = 2 нельзя указать значение pool_lanes_count';
        END IF;
    END IF;

    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_stadium_values_trigger
    BEFORE INSERT OR UPDATE ON place
    FOR EACH ROW
EXECUTE FUNCTION check_stadium_values();

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

INSERT INTO place(id, type_id, name, address, stadium_is_covered, stadium_places_count, stadium_square) VALUES
    (1, 3, 'Стадион школы №228', 'Ленина, 1', true, 75, 100),
    (2, 3, 'Центральный стадион', 'Победы, 14', false, 250, 300);

