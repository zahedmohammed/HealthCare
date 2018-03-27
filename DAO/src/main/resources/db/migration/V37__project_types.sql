
update project set project_type = 'Local' where project_type like 'LOCAL';
update project set project_type = 'Git' where project_type like 'GIT';