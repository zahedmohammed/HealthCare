-- Author: Mohammed Shoukath Ali
ALTER TABLE job DROP COLUMN IF EXISTS issue_tracker;

CREATE TABLE job_notification
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  account character varying(255),
  channel character varying(255),
  name character varying(255),
  CONSTRAINT job_notification_pkey PRIMARY KEY (id)
);

DROP TABLE job_notifications;
CREATE TABLE job_notifications
(
  job_id character varying(255) NOT NULL,
  account character varying(255),
  channel character varying(255),
  name character varying(255),
  to_ character varying(255),
  CONSTRAINT fkjxa80tma6u6y3r9eyhmppd6pe FOREIGN KEY (job_id)
      REFERENCES job (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE job_issue_tracker
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  account character varying(255),
  name character varying(255),
  url character varying(255),
  CONSTRAINT job_issue_tracker_pkey PRIMARY KEY (id)
);

ALTER TABLE ONLY job ADD COLUMN job_issue_tracker_id character varying(255);


ALTER TABLE ONLY job
  ADD CONSTRAINT job_issue_tracker_fk FOREIGN KEY (job_issue_tracker_id) REFERENCES job_issue_tracker(id);

