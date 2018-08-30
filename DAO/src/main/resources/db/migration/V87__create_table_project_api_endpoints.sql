-- Author: Mohammed Luqman Shareef

CREATE TABLE project_api_endpoints
(
  project_id character varying(255) NOT NULL,
  api_endpoints character varying(255),
  CONSTRAINT project_api_endpoints_fk FOREIGN KEY (project_id)
      REFERENCES public.project (id)
 );

