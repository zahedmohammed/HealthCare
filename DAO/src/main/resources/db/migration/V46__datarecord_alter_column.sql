--
-- Name: datarecord Drop columns
-- author Mohammed Shoukath Ali
--


alter table data_set drop constraint fkrb5k700mbpkqfbvsa4sp6gi4q;
ALTER TABLE ONLY data_set DROP COLUMN IF EXISTS org_id;

alter table data_record drop constraint fk2y4jlgy7arkjpuwqk1uyc720a;
alter table data_record drop constraint fkl609hsrkgg5gogjjxd4o7yikk;

ALTER TABLE ONLY data_record DROP COLUMN IF EXISTS created_by;
ALTER TABLE ONLY data_record DROP COLUMN IF EXISTS created_date;
ALTER TABLE ONLY data_record DROP COLUMN IF EXISTS inactive;


ALTER TABLE ONLY data_record DROP COLUMN IF EXISTS modified_by;
ALTER TABLE ONLY data_record DROP COLUMN IF EXISTS modified_date;
ALTER TABLE ONLY data_record DROP COLUMN IF EXISTS org_id;

ALTER TABLE ONLY data_record DROP COLUMN IF EXISTS project_id;
