--
-- Name: RUN_STATS
-- Author: Mohammed Luqman Shareef
--

CREATE TABLE run_stats
(
  run_id character varying(255) NOT NULL,
  value numeric,
  key character varying(255) NOT NULL,
  CONSTRAINT run_stats_pkey PRIMARY KEY (run_id, key),
  CONSTRAINT run_stats_fk FOREIGN KEY (run_id)
      REFERENCES run (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
