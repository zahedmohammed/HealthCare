-- Author: Syed Jaleel

alter table auto_code_generator drop column if exists severity;
alter table auto_code_generator drop column if exists display_header_description;
alter table auto_code_generator drop column if exists display_header_label;
alter table auto_code_generator drop column if exists assertion_description;
