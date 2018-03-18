--
-- Name: project_imports
--


CREATE TABLE project_imports (
    project_id character varying(255) NOT NULL,

    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone
);


CREATE TABLE project_imports_map (
    project_imports_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    val character varying(255) NOT NULL
);


ALTER TABLE ONLY project_imports
    ADD CONSTRAINT project_imports_pkey PRIMARY KEY (id);

ALTER TABLE ONLY project_imports
  ADD CONSTRAINT project_imports_project_fk FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE ONLY project_imports_map
  ADD CONSTRAINT project_imports_map_fk FOREIGN KEY (project_imports_id) REFERENCES project_imports(id);