-- Author: Mohammed Shoukath Ali
-- Table: public.test_case_response_issue_tracker

-- DROP TABLE test_case_response_issue_tracker;

CREATE TABLE test_case_response_issue_tracker
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  issue_id character varying(255),
  status character varying(255),
  test_case_response_issue_tracker_id character varying(255),
  validations integer,
  CONSTRAINT test_case_response_issue_tracker_pkey PRIMARY KEY (id)
);

alter table run add column validations numeric;