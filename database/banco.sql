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

CREATE TABLE IF NOT EXISTS public.valores
(
    id serial NOT NULL,
    veiculo text NOT NULL,
    valor numeric(10, 2) NOT NULL,
    fracao numeric(10, 2) NOT NULL,
    PRIMARY KEY (id)
);

END;
