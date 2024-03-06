CREATE TABLE TODOS
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    score         INT          NOT NULL,
    estimate_time INT          NOT NULL,
    done          BOOLEAN      NOT NULL
);
