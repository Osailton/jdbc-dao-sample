To use this sample, you must create the database using the SQL below:

===================================================

-- Database: jdbc_dao_sample

-- DROP DATABASE IF EXISTS jdbc_dao_sample;

CREATE DATABASE jdbc_dao_sample
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- Table: public.product_type

-- DROP TABLE IF EXISTS public.product_type;

CREATE TABLE IF NOT EXISTS public.product_type
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT product_type_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product_type
    OWNER to postgres;
    
-- Table: public.product

-- DROP TABLE IF EXISTS public.product;

CREATE TABLE IF NOT EXISTS public.product
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(20) COLLATE pg_catalog."default",
    base_price numeric,
    entry_date date,
    product_type integer,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_product_type FOREIGN KEY (product_type)
        REFERENCES public.product_type (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;
-- Index: fki_product_product_type

-- DROP INDEX IF EXISTS public.fki_product_product_type;

CREATE INDEX IF NOT EXISTS fki_product_product_type
    ON public.product USING btree
    (product_type ASC NULLS LAST)
    TABLESPACE pg_default;
    
===================================================


In the sample above I'm creating a Postgresql database, but you can easily adapt
to another database of your preference.

If you use another database, remember to download and install the driver jar and
change the property configuration for "dburl" in db.properties.