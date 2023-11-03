-- Database: base_dev

BEGIN;

CREATE TABLE IF NOT EXISTS public.ticket
(
    id serial NOT NULL,
    codigotag text NOT NULL,
    entrada timestamptz NOT NULL,
    saida timestamptz,
    valor numeric(10, 2),
    PRIMARY KEY (id)
);

END;
