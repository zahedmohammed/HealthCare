
-- author Mohammed Shoukath Ali


-- Table: job_notifications

-- DROP TABLE public.job_notifications;

CREATE TABLE job_notifications
(
  job_id character varying(255) NOT NULL,
  notifications character varying(255),
  CONSTRAINT fkjxa80tma6u6y3r9eyhmppd6pe FOREIGN KEY (job_id)
      REFERENCES public.job (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
