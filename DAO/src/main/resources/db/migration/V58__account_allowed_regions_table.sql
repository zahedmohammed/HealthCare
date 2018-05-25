--
-- Author:Mohammed Shoukath Ali
--

-- Table: account_allowed_regions

DROP TABLE If exists account_allowed_regions;

CREATE TABLE account_allowed_regions
(
  account_id character varying(255) NOT NULL,
  allowed_regions character varying(255)
);


