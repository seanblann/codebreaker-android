CREATE TABLE IF NOT EXISTS `game`
(
    `game_id`    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `serviceKey` TEXT,
    `created`    INTEGER                           NOT NULL,
    `pool`       TEXT                              NOT NULL,
    `length`     INTEGER                           NOT NULL
);

CREATE INDEX IF NOT EXISTS `index_game_created` ON `game` (`created`);

CREATE TABLE IF NOT EXISTS `guess`
(
    `guess_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `created`       INTEGER                           NOT NULL,
    `text`          TEXT                              NOT NULL,
    `exact_matches` INTEGER                           NOT NULL,
    `near_matches`  INTEGER                           NOT NULL,
    `solution`      INTEGER                           NOT NULL,
    `game_id`       INTEGER                           NOT NULL,
    FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_guess_game_id_created` ON `guess` (`game_id`, `created`);

CREATE INDEX IF NOT EXISTS `index_guess_game_id` ON `guess` (`game_id`);

CREATE VIEW `game_summary` AS
SELECT gs.length,
       COUNT(*)                                                AS count_games,
       MIN(gs.guess_count)                                     AS min_guesses,
       MAX(gs.guess_count)                                     AS max_guesses,
       AVG(gs.guess_count)                                     AS avg_guesses,
       MIN(gs.last_guess_timestamp - gs.first_guess_timestamp) AS min_time,
       MAX(gs.last_guess_timestamp - gs.first_guess_timestamp) AS max_time,
       AVG(gs.last_guess_timestamp - gs.first_guess_timestamp) AS avg_time
FROM (
         SELECT ga.length,
                gu.game_id,
                COUNT(*)        AS guess_count,
                MIN(gu.created) AS first_guess_timestamp,
                MAX(gu.created) AS last_guess_timestamp
         FROM guess AS gu
                  INNER JOIN game AS ga
                             ON gu.game_id = ga.game_id
         GROUP BY ga.length,
                  gu.game_id
     ) AS gs
GROUP BY gs.length;

CREATE VIEW `game_performance` AS
SELECT g.game_id,
       g.created,
       g.length,
       (gs.last_guess_timestamp - gs.first_guess_timestamp) AS duration,
       gs.guess_count
FROM game AS g
         JOIN (
    SELECT game_id,
           MIN(created) AS first_guess_timestamp,
           MAX(created) AS last_guess_timestamp,
           COUNT(*)     AS guess_count
    FROM guess
    GROUP BY game_id
) AS gs
              ON gs.game_id = g.game_id;

