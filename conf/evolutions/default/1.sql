# --- !Ups

CREATE TABLE properties (
  id                        serial PRIMARY KEY,
  keywords                  text[] NOT NULL,
  title                     text NOT NULL,
  price                     integer NOT NULL,
  property_type             text NOT NULL,
  updated_in_days           smallint NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS properties;