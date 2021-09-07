CREATE DATABASE cashbox
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE TABLE public.users
(
    name        character varying(30)  NOT NULL,
    email       character varying(100) NOT NULL,
    accesslevel integer                NOT NULL,
    locale      character varying(5)   NOT NULL,
    hash        bytea[]                NOT NULL,
    PRIMARY KEY (name)
);
ALTER TABLE public.users
    OWNER to postgres;
/*
 Заполнение таблици locale
 */
INSERT INTO locale (id, name, description)
VALUES (1, 'ua', 'Украхнська');
INSERT INTO locale (id, name, description)
VALUES (2, 'ru', 'Русский');
INSERT INTO locale (id, name, description)
VALUES (3, 'en', 'English');
INSERT INTO locale (id, name, description)
VALUES (4, 'pl', 'Język polski');

/*
 Заполнение таблици role
 */
INSERT INTO role (id, name)
VALUES (1, 'Admin');
INSERT INTO role (id, name)
VALUES (2, 'Cashier');
INSERT INTO role (id, name)
VALUES (3, 'Senior cashier');
INSERT INTO role (id, name)
VALUES (4, 'Commodity expert');
INSERT INTO role (id, name)
VALUES (5, 'Guest');

/*
 Заполнение таблицу user
 */
INSERT INTO "user" (id, name, role_id, locale_id, activated, hash)
VALUES (1, 'admin', 1,2,true,'');
INSERT INTO "user" (id, name, role_id, locale_id, activated, hash)
VALUES (2, 'cashier', 2,3,true,'');
INSERT INTO "user" (id, name, role_id, locale_id, activated, hash)
VALUES (3, 'senior', 3,2,true,'');
INSERT INTO "user" (id, name, role_id, locale_id, activated, hash)
VALUES (4, 'cashier', 4,1,true,'');