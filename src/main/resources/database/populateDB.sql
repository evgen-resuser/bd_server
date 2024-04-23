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

INSERT INTO place(id, type_id, name, address, stadium_is_covered, stadium_places_count, stadium_square) VALUES
(1, 3, 'Стадион школы №228', 'Ленина, 1', true, 75, 100),
(2, 3, 'Центральный стадион', 'Победы, 14', false, 250, 300);


