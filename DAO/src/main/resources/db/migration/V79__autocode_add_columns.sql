--
-- description columns to git
--


ALTER TABLE auto_code_generator DROP COLUMN if EXISTS display_header_description;
ALTER TABLE ONLY auto_code_generator ADD COLUMN display_header_description text;

ALTER TABLE auto_code_generator DROP COLUMN if EXISTS display_header_label;
ALTER TABLE ONLY auto_code_generator ADD COLUMN display_header_label character varying(255);

ALTER TABLE auto_code_generator DROP COLUMN if EXISTS assertion_description;
ALTER TABLE ONLY auto_code_generator ADD COLUMN assertion_description text;

