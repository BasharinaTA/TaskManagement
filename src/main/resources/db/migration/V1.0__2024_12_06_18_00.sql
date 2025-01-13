CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    email         VARCHAR(320)          NOT NULL UNIQUE,
    hash_password VARCHAR(100)          NOT NULL,
    name          VARCHAR(30)           NOT NULL,
    surname       VARCHAR(30)           NOT NULL,
    role          VARCHAR(20)           NOT NULL,
    created       TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    header      VARCHAR(50)           NOT NULL,
    description VARCHAR(500)          NOT NULL,
    status      VARCHAR(25)           NOT NULL,
    priority    VARCHAR(15)           NOT NULL,
    author_id   BIGINT                NOT NULL REFERENCES users (id),
    executor_id BIGINT                NOT NULL REFERENCES users (id),
    created     TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    text      VARCHAR(500)          NOT NULL,
    task_id   BIGINT                NOT NULL REFERENCES tasks (id),
    author_id BIGINT                NOT NULL REFERENCES users (id),
    created   TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (email, hash_password, name, surname, role)
VALUES ('ivanov@gmail.com', '$2a$12$Cx4ufvJHSYAePAY5fYO3zu9QPDTQbZa8VJNwXWhtUzqFqRHav3/pG', 'Иван', 'Иванов',
        'ROLE_ADMIN');
INSERT INTO users (email, hash_password, name, surname, role)
VALUES ('borisov@gmail.com', '$2y$12$HvJ3Td4NSbWBXSTh7PDCbePpEKtP/nKkCwpz27ybCtRlzs7qhAXnS', 'Борис', 'Борисов',
        'ROLE_USER');
INSERT INTO users (email, hash_password, name, surname, role)
VALUES ('maksimov@gmail.com', '$2y$12$jy/RPuhmRUIiTtRHnViP0OLCkJbdFLN/rq68CsQdnyxMFzT1LATJu', 'Максим', 'Максимов',
        'ROLE_USER');
INSERT INTO users (email, hash_password, name, surname, role)
VALUES ('smirnova@gmail.com', '$2y$12$utiqfamlETK4.DqrtcM3K.WNpYCPto98fkbZskWLcTQPvhCv0NBeK', 'Елена', 'Смирнова',
        'ROLE_USER');


INSERT INTO tasks (header, description, status, priority, author_id, executor_id)
VALUES ('Заголовок задачи 1', 'Описание задачи 1', 'OPEN', 'LOW', 1, 2);
INSERT INTO tasks (header, description, status, priority, author_id, executor_id)
VALUES ('Заголовок задачи 2', 'Описание задачи 2', 'IN_PROGRESS', 'MEDIUM', 1, 3);
INSERT INTO tasks (header, description, status, priority, author_id, executor_id)
VALUES ('Заголовок задачи 3', 'Описание задачи 3', 'CLOSED', 'HIGH', 3, 4);
INSERT INTO tasks (header, description, status, priority, author_id, executor_id)
VALUES ('Заголовок задачи 4', 'Описание задачи 4', 'OPEN', 'MEDIUM', 1, 3);
INSERT INTO tasks (header, description, status, priority, author_id, executor_id)
VALUES ('Заголовок задачи 5', 'Описание задачи 5', 'IN_PROGRESS', 'LOW', 1, 4);

INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 1 задачи 1', 1, 1);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 1 задачи 2', 2, 1);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 2 задачи 2', 2, 3);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 3 задачи 2', 2, 1);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 1 задачи 3', 3, 3);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 2 задачи 3', 3, 4);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 3 задачи 3', 3, 3);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 4 задачи 3', 3, 4);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 1 задачи 4', 4, 1);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 2 задачи 4', 4, 3);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 3 задачи 4', 4, 1);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 1 задачи 5', 5, 1);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 2 задачи 5', 5, 4);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 3 задачи 5', 5, 4);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 4 задачи 5', 5, 1);
INSERT INTO comments (text, task_id, author_id)
VALUES ('Комментарий 5 задачи 5', 5, 4);