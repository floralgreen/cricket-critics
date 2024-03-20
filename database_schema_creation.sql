#tabella movies
CREATE TABLE if not exists cricketcritics_local.movies
(
    id              BIGINT                     NOT NULL,
    title           varchar(255)               NOT NULL,
    plot            varchar(2000)              NULL,
    language        ENUM ('1','2','3','4','5') NOT NULL,
    duration        INT                        NOT NULL,
    users_score     INT                        NOT NULL,
    reviewers_score INT                        NOT NULL,
    release_date    DATE                       NULL,
    movie_website   varchar(500)               NULL,
    record_status   ENUM ('A','D') DEFAULT 'A' NOT NULL,
    category        ENUM ('ACTION', 'HORROR', 'FANTASY', 'SCI-FI', 'COMEDY', 'DRAMATIC', 'THRILLER', 'ROMANTIC', 'CRIME', 'ANIMATION', 'DOCUMENTARY'),
    CONSTRAINT movies_pk PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

#tabella users
CREATE TABLE if not exists cricketcritics_local.users
(
    id            BIGINT                     NOT NULL,
    user_name     varchar(30)                NOT NULL,
    name          varchar(255)               NOT NULL,
    last_name     varchar(255)               NOT NULL,
    email         varchar(255)               NOT NULL,
    password      varchar(500)               NOT NULL,
    record_status enum ('A','D') DEFAULT 'A' NULL,
    user_enum     enum ('1','2','3')         NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT users_email_unique UNIQUE KEY (email)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

#tabella reviews
CREATE TABLE if not exists cricketcritics_local.reviews
(
    id            BIGINT                     NOT NULL,
    user_id       BIGINT                     NOT NULL,
    movie_id      BIGINT                     NOT NULL,
    description   varchar(5000)              NULL,
    score         INT                        NOT NULL,
    review_rating ENUM ('1','2','3','4','5') NULL,
    review_date   DATETIME                   NOT NULL,
    record_status ENUM ('A','D') DEFAULT 'A' NOT NULL,
    CONSTRAINT reviews_pk PRIMARY KEY (id),
    CONSTRAINT review_movie_fk foreign key (movie_id) references movies (id),
    CONSTRAINT review_user_fk foreign key (user_id) references users (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


#tabella actors
CREATE TABLE if not exists cricketcritics_local.actors
(
    id             BIGINT       NOT NULL,
    name           varchar(255) NOT NULL,
    last_name      varchar(255) NOT NULL,
    date_of_birth  DATE         NULL,
    nationality    varchar(255) NULL,
    age            INT          NOT NULL,
    place_of_birth varchar(255) NULL,
    CONSTRAINT actors_pk PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

#tabella directors
CREATE TABLE if not exists cricketcritics_local.directors
(
    id             BIGINT       NOT NULL,
    name           varchar(255) NOT NULL,
    last_name      varchar(255) NOT NULL,
    date_of_birth  DATE         NULL,
    nationality    varchar(255) NULL,
    age            INT          NOT NULL,
    place_of_birth varchar(255) NULL,
    CONSTRAINT actors_pk PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

#tabella congiunzione actors e movies per N:N
CREATE TABLE if not exists cricketcritics_local.actors_movies
(
    movie_id BIGINT NOT NULL,
    actor_id BIGINT NOT null,
    constraint movie_actor_fk foreign key (movie_id) references movies (id),
    constraint actor_movie_fk foreign key (actor_id) references actors (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

#tabella congiunzione directors e movies per N:N
CREATE TABLE if not exists cricketcritics_local.directors_movies
(
    movie_id    BIGINT NOT NULL,
    director_id BIGINT NOT null,
    constraint movie_director_fk foreign key (movie_id) references movies (id),
    constraint director_movie_fk foreign key (director_id) references directors (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;





