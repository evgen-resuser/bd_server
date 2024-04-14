INSERT INTO sport(name) VALUES
('Футбол'),
('Баскетбол'),
('Волейбол'),
('Бег с препятствиями'),
('Прыжки в воду'),
('Водное поло');

INSERT INTO coach(name) VALUES
('Водный В.В.'),
('Андреев А.А.');

INSERT INTO coach_sport(coach_id, sport_id) VALUES
(1, 5),
(1, 6),
(2, 1),
(2, 4);

INSERT INTO club(name) VALUES
('Водоплавающие'),
('Кенгурята');

INSERT INTO sportsman(name, club_id) VALUES
('Васин А.Б.', 1);
INSERT INTO sportsman(name) VALUES
('Петров П.П.');

INSERT INTO coach_sportsman(coach_id, sportsman_id) VALUES
(1, 1);

INSERT INTO place(id, type_id, name, address) VALUES
                                                  (17, 3, 'Стадион школы №228', 'Ленина, 1'),
                                                  (1, 2, 'Фитнес-центр "Скала"', 'Металлургов, 34'),
                                                  (1, 3, 'Центральный стадион', 'Победы, 14'),
                                                  (1, 1, 'Бассейн "Болото"', 'Мира, 28');

INSERT INTO stadium(id, is_covered, places_count, square) VALUES
                                                              (17, true, 250, 300),
                                                              (1, false, 75, 100);

INSERT INTO gym(id, square) VALUES
    (1, 60);

INSERT INTO pool(id, length, depth, lanes_count) VALUES
    (1, 25, 3, 6);
