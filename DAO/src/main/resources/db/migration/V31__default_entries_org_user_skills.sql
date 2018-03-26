-- Default entries
--   Org: Default Org
--   User: admin@fxlabs.local
--   Skills:  VC, Cloud


INSERT INTO org(
            id, created_by, created_date, inactive, modified_by, modified_date,
            billing_email, company, description, location, name, org_plan,
            org_type)
    VALUES ('4028b881620688c001620689a3210000', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp, 'admin@fxlabs.local',
            'Default', 'Default Org', null, null, 'Default Org', null);


INSERT INTO users(
            id, created_by, created_date, inactive, modified_by, modified_date,
            account_non_expired, account_non_locked, company, credentials_non_expired,
            email, enabled, job_title, location, name, password, username)
    VALUES ('4028b881620688c001620689a3210010', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
            true, true, 'Default', true,
            'admin@fxlabs.local', true, 'Administrator', null, 'Administrator', 'fxadmin123', 'admin@fxlabs.local');


INSERT INTO users_password(
            id, created_by, created_date, inactive, modified_by, modified_date,
            active, grant_key, password, users_id)
    VALUES ('4028b881620688c001620689a3210011', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
            true, 'fxadmin123', '$2a$11$9yDdvUVFTFw./QIfBt8OZePjAT1lBuRh611AtZjs0/g4Abe2TKwtu', '4028b881620688c001620689a3210010');




INSERT INTO users_privileges(users_id, privileges)
    VALUES ('4028b881620688c001620689a3210010', 'ROLE_SUPER');


INSERT INTO users_privileges(users_id, privileges)
    VALUES ('4028b881620688c001620689a3210010', 'ROLE_USER');


INSERT INTO users_privileges(users_id, privileges)
    VALUES ('4028b881620688c001620689a3210010', 'ROLE_ENTERPRISE');


INSERT INTO users_privileges(users_id, privileges)
    VALUES ('4028b881620688c001620689a3210010', 'ROLE_DEVELOPER');


INSERT INTO users_privileges(users_id, privileges)
    VALUES ('4028b881620688c001620689a3210010', 'ROLE_ADMIN');

--- Issue Tracker Skills

INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010000', 'JIRA', 'JIRA Issue Tracker', 'fx-itaas-jira', 'ISSUE_TRACKER', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);



INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010002', 'GIT', 'Github Issue Tracker', 'fx-itaas-github', 'ISSUE_TRACKER', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);


-- Cloud Skills for Execution Bots deployment

INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010010', 'EC2', 'AWS EC2 Cloud Skill', 'fx-caas-aws-ec2', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);


INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010011', 'Azure', 'Micrososft Azure Cloud Skill', 'fx-caas-azure', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);

INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010012', 'GCP', 'Google Cloud Skill', 'fx-caas-gcp', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);

INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010013', 'DigitalOcean', 'Digital Ocean Skill', 'fx-caas-do', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);

INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010014', 'IBM', 'IBM Softlayer Skill', 'fx-caas-ibm', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);


INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010015', 'Rackspace', 'Rackspace Softlayer Skill', 'fx-caas-rackspace', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);


INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010016', 'Oracle', 'Oracle Skill', 'fx-caas-oracle', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);


INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010017', 'vShpere', 'vSphere Skill', 'fx-caas-vsphere', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);

INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010018', 'OpenStack', 'Open Stack Skill', 'fx-caas-os', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);


INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010019', 'DockerSwarm', 'Docker Swarm Skill', 'fx-caas-ds', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);

INSERT INTO skill(
            id, name, description, _key, skill_type, created_by, created_date,
            inactive, modified_by, modified_date, org_id, access_key, secret_key,
            host, prop1, prop2, prop3, prop4, prop5)
    VALUES ('4028b881620688c001620689a0010020', 'Kubernetes', 'Kubernetes Skill', 'fx-caas-k8', 'BOT_DEPLOYMENT', 'Admin' ,current_timestamp, false, 'Admin', current_timestamp,
			'4028b881620688c001620689a3210000', null, null,
            null, null, null, null, null, null);
