--
-- Name: alert; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE alert (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    healed_date timestamp without time zone,
    message text,
    read_date timestamp without time zone,
    ref_id character varying(255),
    ref_name character varying(255),
    ref_type character varying(255),
    status character varying(255),
    subject character varying(255),
    task_state character varying(255),
    task_type character varying(255),
    type character varying(255)
);


--
-- Name: alert_users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE alert_users (
    alert_id character varying(255) NOT NULL,
    users character varying(255)
);


--
-- Name: cluster; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE cluster (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    cloud_type character varying(255),
    driver character varying(255),
    key character varying(255),
    name character varying(255),
    region character varying(255),
    status character varying(255),
    visibility character varying(255),
    org_id character varying(255)
);


--
-- Name: cluster_ping; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE cluster_ping (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    bot_id character varying(255),
    key character varying(255),
    total_v_bots integer
);


--
-- Name: environment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE environment (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    base_url character varying(255),
    description character varying(255),
    name character varying(255),
    project_id character varying(255),
    ref_id character varying(255)
);


--
-- Name: environment_auths; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE environment_auths (
    environment_id character varying(255) NOT NULL,
    auth_type character varying(255),
    name character varying(255),
    password character varying(255),
    username character varying(255)
);


--
-- Name: job; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE job (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    cron character varying(255),
    description character varying(255),
    environment character varying(255),
    name character varying(255),
    next_fire timestamp without time zone,
    ref_id character varying(255),
    regions character varying(255),
    project_id character varying(255)
);


--
-- Name: job_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE job_tags (
    job_id character varying(255) NOT NULL,
    tags character varying(255)
);


--
-- Name: org; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE org (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    billing_email character varying(255),
    company character varying(255),
    description character varying(255),
    location character varying(255),
    name character varying(255),
    org_plan character varying(255),
    org_type character varying(255)
);


--
-- Name: org_users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE org_users (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    org_role character varying(255),
    status character varying(255),
    org_id character varying(255),
    users_id character varying(255)
);


--
-- Name: project; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE project (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    description character varying(255),
    last_sync timestamp without time zone,
    name character varying(255),
    project_type character varying(255),
    visibility character varying(255),
    org_id character varying(255)
);


--
-- Name: project_environments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE project_environments (
    project_id character varying(255) NOT NULL,
    environments_id character varying(255) NOT NULL
);


--
-- Name: project_file; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE project_file (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    checksum character varying(255),
    content text,
    filename character varying(255),
    modified timestamp without time zone,
    project_id character varying(255)
);


--
-- Name: project_git_account; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE project_git_account (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    branch character varying(255),
    last_commit character varying(255),
    password character varying(255),
    project_id character varying(255),
    url character varying(255),
    username character varying(255)
);


--
-- Name: project_jobs; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE project_jobs (
    project_id character varying(255) NOT NULL,
    jobs_id character varying(255) NOT NULL
);


--
-- Name: project_licenses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE project_licenses (
    project_id character varying(255) NOT NULL,
    licenses character varying(255)
);


--
-- Name: project_users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE project_users (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    role character varying(255),
    project_id character varying(255),
    users_id character varying(255)
);


--
-- Name: qrtz_blob_triggers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_blob_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    blob_data bytea
);


--
-- Name: qrtz_calendars; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_calendars (
    sched_name character varying(120) NOT NULL,
    calendar_name character varying(200) NOT NULL,
    calendar bytea NOT NULL
);


--
-- Name: qrtz_cron_triggers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_cron_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    cron_expression character varying(120) NOT NULL,
    time_zone_id character varying(80)
);


--
-- Name: qrtz_fired_triggers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_fired_triggers (
    sched_name character varying(120) NOT NULL,
    entry_id character varying(95) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    instance_name character varying(200) NOT NULL,
    fired_time bigint NOT NULL,
    sched_time bigint NOT NULL,
    priority integer NOT NULL,
    state character varying(16) NOT NULL,
    job_name character varying(200),
    job_group character varying(200),
    is_nonconcurrent boolean,
    requests_recovery boolean
);


--
-- Name: qrtz_job_details; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_job_details (
    sched_name character varying(120) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    job_class_name character varying(250) NOT NULL,
    is_durable boolean NOT NULL,
    is_nonconcurrent boolean NOT NULL,
    is_update_data boolean NOT NULL,
    requests_recovery boolean NOT NULL,
    job_data bytea
);


--
-- Name: qrtz_locks; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_locks (
    sched_name character varying(120) NOT NULL,
    lock_name character varying(40) NOT NULL
);


--
-- Name: qrtz_paused_trigger_grps; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_paused_trigger_grps (
    sched_name character varying(120) NOT NULL,
    trigger_group character varying(200) NOT NULL
);


--
-- Name: qrtz_scheduler_state; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_scheduler_state (
    sched_name character varying(120) NOT NULL,
    instance_name character varying(200) NOT NULL,
    last_checkin_time bigint NOT NULL,
    checkin_interval bigint NOT NULL
);


--
-- Name: qrtz_simple_triggers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_simple_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    repeat_count bigint NOT NULL,
    repeat_interval bigint NOT NULL,
    times_triggered bigint NOT NULL
);


--
-- Name: qrtz_simprop_triggers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_simprop_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    str_prop_1 character varying(512),
    str_prop_2 character varying(512),
    str_prop_3 character varying(512),
    int_prop_1 integer,
    int_prop_2 integer,
    long_prop_1 bigint,
    long_prop_2 bigint,
    dec_prop_1 numeric(13,4),
    dec_prop_2 numeric(13,4),
    bool_prop_1 boolean,
    bool_prop_2 boolean
);


--
-- Name: qrtz_triggers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE qrtz_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority integer,
    trigger_state character varying(16) NOT NULL,
    trigger_type character varying(8) NOT NULL,
    start_time bigint NOT NULL,
    end_time bigint,
    calendar_name character varying(200),
    misfire_instr smallint,
    job_data bytea
);


--
-- Name: run; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE run (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    run_id bigint,
    description character varying(255),
    end_time timestamp without time zone,
    failed_tests bigint,
    name character varying(255),
    skipped_tests bigint,
    start_time timestamp without time zone,
    status character varying(255),
    total_suite_completed bigint,
    total_test_completed bigint,
    total_tests bigint,
    total_time bigint,
    job_id character varying(255)
);


--
-- Name: run_attributes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE run_attributes (
    run_id character varying(255) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


--
-- Name: system_setting; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE system_setting (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    key character varying(255),
    label character varying(255),
    value character varying(255)
);


--
-- Name: test_suite; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    auth character varying(255),
    endpoint character varying(255),
    method character varying(255),
    name character varying(255),
    cleanup_exec character varying(255),
    init_exec character varying(255),
    logger character varying(255),
    repeat integer,
    repeat_delay bigint,
    repeat_on_failure integer,
    timeout_seconds bigint,
    project_id character varying(255),
    type character varying(255)
);


--
-- Name: test_suite_assertions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_assertions (
    test_suite_id character varying(255) NOT NULL,
    assertions character varying(255)
);


--
-- Name: test_suite_authors; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_authors (
    test_suite_id character varying(255) NOT NULL,
    authors character varying(255)
);


--
-- Name: test_suite_cleanup; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_cleanup (
    test_suite_id character varying(255) NOT NULL,
    cleanup character varying(255)
);


--
-- Name: test_suite_headers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_headers (
    test_suite_id character varying(255) NOT NULL,
    headers character varying(255)
);


--
-- Name: test_suite_init; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_init (
    test_suite_id character varying(255) NOT NULL,
    init character varying(255)
);


--
-- Name: test_suite_request; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_request (
    test_suite_id character varying(255) NOT NULL,
    request text
);


--
-- Name: test_suite_requests; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_requests (
    test_suite_id character varying(255) NOT NULL,
    requests text
);


--
-- Name: test_suite_response; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_response (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    logs text,
    region character varying(255),
    request_end_time timestamp without time zone,
    request_start_time timestamp without time zone,
    request_time bigint,
    response character varying(255),
    run_id character varying(255),
    status character varying(255),
    test_suite character varying(255),
    tests integer,
    total_failed bigint,
    total_passed bigint,
    total_skipped bigint
);


--
-- Name: test_suite_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_suite_tags (
    test_suite_id character varying(255) NOT NULL,
    tags character varying(255)
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    account_non_expired boolean NOT NULL,
    account_non_locked boolean NOT NULL,
    company character varying(255),
    credentials_non_expired boolean NOT NULL,
    email character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    job_title character varying(255),
    location character varying(255),
    name character varying(255),
    password character varying(255),
    username character varying(64) NOT NULL
);


--
-- Name: users_password; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users_password (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    active boolean NOT NULL,
    grant_key character varying(255),
    password character varying(255),
    users_id character varying(255)
);


--
-- Name: users_privileges; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users_privileges (
    users_id character varying(255) NOT NULL,
    privileges character varying(255)
);


--
-- Name: alert alert_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY alert
    ADD CONSTRAINT alert_pkey PRIMARY KEY (id);


--
-- Name: cluster_ping cluster_ping_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cluster_ping
    ADD CONSTRAINT cluster_ping_pkey PRIMARY KEY (id);


--
-- Name: cluster cluster_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cluster
    ADD CONSTRAINT cluster_pkey PRIMARY KEY (id);


--
-- Name: environment environment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY environment
    ADD CONSTRAINT environment_pkey PRIMARY KEY (id);


--
-- Name: job job_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job
    ADD CONSTRAINT job_pkey PRIMARY KEY (id);


--
-- Name: org org_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY org
    ADD CONSTRAINT org_pkey PRIMARY KEY (id);


--
-- Name: org_users org_users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY org_users
    ADD CONSTRAINT org_users_pkey PRIMARY KEY (id);


--
-- Name: project_file project_file_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_file
    ADD CONSTRAINT project_file_pkey PRIMARY KEY (id);


--
-- Name: project_git_account project_git_account_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_git_account
    ADD CONSTRAINT project_git_account_pkey PRIMARY KEY (id);


--
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- Name: project_users project_users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_users
    ADD CONSTRAINT project_users_pkey PRIMARY KEY (id);


--
-- Name: qrtz_blob_triggers qrtz_blob_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_blob_triggers
    ADD CONSTRAINT qrtz_blob_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_calendars qrtz_calendars_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_calendars
    ADD CONSTRAINT qrtz_calendars_pkey PRIMARY KEY (sched_name, calendar_name);


--
-- Name: qrtz_cron_triggers qrtz_cron_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_cron_triggers
    ADD CONSTRAINT qrtz_cron_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_fired_triggers qrtz_fired_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_fired_triggers
    ADD CONSTRAINT qrtz_fired_triggers_pkey PRIMARY KEY (sched_name, entry_id);


--
-- Name: qrtz_job_details qrtz_job_details_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_job_details
    ADD CONSTRAINT qrtz_job_details_pkey PRIMARY KEY (sched_name, job_name, job_group);


--
-- Name: qrtz_locks qrtz_locks_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_locks
    ADD CONSTRAINT qrtz_locks_pkey PRIMARY KEY (sched_name, lock_name);


--
-- Name: qrtz_paused_trigger_grps qrtz_paused_trigger_grps_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_paused_trigger_grps
    ADD CONSTRAINT qrtz_paused_trigger_grps_pkey PRIMARY KEY (sched_name, trigger_group);


--
-- Name: qrtz_scheduler_state qrtz_scheduler_state_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_scheduler_state
    ADD CONSTRAINT qrtz_scheduler_state_pkey PRIMARY KEY (sched_name, instance_name);


--
-- Name: qrtz_simple_triggers qrtz_simple_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_simple_triggers
    ADD CONSTRAINT qrtz_simple_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_simprop_triggers qrtz_simprop_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_simprop_triggers
    ADD CONSTRAINT qrtz_simprop_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_triggers qrtz_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_triggers
    ADD CONSTRAINT qrtz_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- Name: run_attributes run_attributes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY run_attributes
    ADD CONSTRAINT run_attributes_pkey PRIMARY KEY (run_id, name);


--
-- Name: run run_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY run
    ADD CONSTRAINT run_pkey PRIMARY KEY (id);


--
-- Name: system_setting system_setting_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY system_setting
    ADD CONSTRAINT system_setting_pkey PRIMARY KEY (id);


--
-- Name: test_suite test_suite_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite
    ADD CONSTRAINT test_suite_pkey PRIMARY KEY (id);


--
-- Name: test_suite_response test_suite_response_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_response
    ADD CONSTRAINT test_suite_response_pkey PRIMARY KEY (id);


--
-- Name: project_jobs uk_2tofuagka8er4qgmqld4mts51; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_jobs
    ADD CONSTRAINT uk_2tofuagka8er4qgmqld4mts51 UNIQUE (jobs_id);


--
-- Name: project_environments uk_4jwe16qrbq7nfmauc75cp3ik9; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_environments
    ADD CONSTRAINT uk_4jwe16qrbq7nfmauc75cp3ik9 UNIQUE (environments_id);


--
-- Name: users_password users_password_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_password
    ADD CONSTRAINT users_password_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: idx_qrtz_ft_inst_job_req_rcvry; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_ft_inst_job_req_rcvry ON qrtz_fired_triggers USING btree (sched_name, instance_name, requests_recovery);


--
-- Name: idx_qrtz_ft_j_g; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_ft_j_g ON qrtz_fired_triggers USING btree (sched_name, job_name, job_group);


--
-- Name: idx_qrtz_ft_jg; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_ft_jg ON qrtz_fired_triggers USING btree (sched_name, job_group);


--
-- Name: idx_qrtz_ft_t_g; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_ft_t_g ON qrtz_fired_triggers USING btree (sched_name, trigger_name, trigger_group);


--
-- Name: idx_qrtz_ft_tg; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_ft_tg ON qrtz_fired_triggers USING btree (sched_name, trigger_group);


--
-- Name: idx_qrtz_ft_trig_inst_name; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_ft_trig_inst_name ON qrtz_fired_triggers USING btree (sched_name, instance_name);


--
-- Name: idx_qrtz_j_grp; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_j_grp ON qrtz_job_details USING btree (sched_name, job_group);


--
-- Name: idx_qrtz_j_req_recovery; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_j_req_recovery ON qrtz_job_details USING btree (sched_name, requests_recovery);


--
-- Name: idx_qrtz_t_c; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_c ON qrtz_triggers USING btree (sched_name, calendar_name);


--
-- Name: idx_qrtz_t_g; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_g ON qrtz_triggers USING btree (sched_name, trigger_group);


--
-- Name: idx_qrtz_t_j; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_j ON qrtz_triggers USING btree (sched_name, job_name, job_group);


--
-- Name: idx_qrtz_t_jg; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_jg ON qrtz_triggers USING btree (sched_name, job_group);


--
-- Name: idx_qrtz_t_n_g_state; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_n_g_state ON qrtz_triggers USING btree (sched_name, trigger_group, trigger_state);


--
-- Name: idx_qrtz_t_n_state; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_n_state ON qrtz_triggers USING btree (sched_name, trigger_name, trigger_group, trigger_state);


--
-- Name: idx_qrtz_t_next_fire_time; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_next_fire_time ON qrtz_triggers USING btree (sched_name, next_fire_time);


--
-- Name: idx_qrtz_t_nft_misfire; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_nft_misfire ON qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time);


--
-- Name: idx_qrtz_t_nft_st; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_nft_st ON qrtz_triggers USING btree (sched_name, trigger_state, next_fire_time);


--
-- Name: idx_qrtz_t_nft_st_misfire; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_nft_st_misfire ON qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_state);


--
-- Name: idx_qrtz_t_nft_st_misfire_grp; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_nft_st_misfire_grp ON qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);


--
-- Name: idx_qrtz_t_state; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_qrtz_t_state ON qrtz_triggers USING btree (sched_name, trigger_state);


--
-- Name: project_users fk2lsv8p5wntimsy8omcbnscm7m; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_users
    ADD CONSTRAINT fk2lsv8p5wntimsy8omcbnscm7m FOREIGN KEY (users_id) REFERENCES users(id);


--
-- Name: environment_auths fk4w0k5rcrfoa64fnj3b7nl6d0q; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY environment_auths
    ADD CONSTRAINT fk4w0k5rcrfoa64fnj3b7nl6d0q FOREIGN KEY (environment_id) REFERENCES environment(id);


--
-- Name: org_users fk57q0ppr16gltwse0t7t9k5278; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY org_users
    ADD CONSTRAINT fk57q0ppr16gltwse0t7t9k5278 FOREIGN KEY (users_id) REFERENCES users(id);


--
-- Name: test_suite_tags fk6id9dlnu6vi9o4xb3lp7frgsx; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_tags
    ADD CONSTRAINT fk6id9dlnu6vi9o4xb3lp7frgsx FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);


--
-- Name: alert_users fk8si1tbeenhxtjoegkjnitx87q; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY alert_users
    ADD CONSTRAINT fk8si1tbeenhxtjoegkjnitx87q FOREIGN KEY (alert_id) REFERENCES alert(id);


--
-- Name: project_users fk9at0ei37rls7vd2m6sh92668h; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_users
    ADD CONSTRAINT fk9at0ei37rls7vd2m6sh92668h FOREIGN KEY (project_id) REFERENCES project(id);


--
-- Name: job fkarjdud1mbnhkes90edvyq2h14; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fkarjdud1mbnhkes90edvyq2h14 FOREIGN KEY (project_id) REFERENCES project(id);


--
-- Name: test_suite_cleanup fkbki1s4chk93tv4snk3q0bfye3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_cleanup
    ADD CONSTRAINT fkbki1s4chk93tv4snk3q0bfye3 FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);


--
-- Name: job_tags fkdcfwlgp4estlj6fu2btxlm9p6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY job_tags
    ADD CONSTRAINT fkdcfwlgp4estlj6fu2btxlm9p6 FOREIGN KEY (job_id) REFERENCES job(id);


--
-- Name: run fkg9pq5w6yp29s73hcbcqetj2bj; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY run
    ADD CONSTRAINT fkg9pq5w6yp29s73hcbcqetj2bj FOREIGN KEY (job_id) REFERENCES job(id);


--
-- Name: cluster fkgncgrgcxgdj83v7novm2ygbde; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY cluster
    ADD CONSTRAINT fkgncgrgcxgdj83v7novm2ygbde FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: test_suite_requests fkijus4gb5uh92v0r35gon548c7; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_requests
    ADD CONSTRAINT fkijus4gb5uh92v0r35gon548c7 FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);


--
-- Name: project fkl1hmxiqig3psd3lru81kw9qv5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fkl1hmxiqig3psd3lru81kw9qv5 FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: test_suite_init fklhpsaasj7w02sb48fsejqmrbx; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_init
    ADD CONSTRAINT fklhpsaasj7w02sb48fsejqmrbx FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);


--
-- Name: project_licenses fkmpdg727382ulmyedtk5y2duvl; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY project_licenses
    ADD CONSTRAINT fkmpdg727382ulmyedtk5y2duvl FOREIGN KEY (project_id) REFERENCES project(id);


--
-- Name: users_password fkmy45ycxfosshck0aep1pt29gv; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_password
    ADD CONSTRAINT fkmy45ycxfosshck0aep1pt29gv FOREIGN KEY (users_id) REFERENCES users(id);


--
-- Name: org_users fknjj66d38dx3r7kum1f22g85gq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY org_users
    ADD CONSTRAINT fknjj66d38dx3r7kum1f22g85gq FOREIGN KEY (org_id) REFERENCES org(id);


--
-- Name: users_privileges fko3je5dm7n0iprepwx1hm94gwf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_privileges
    ADD CONSTRAINT fko3je5dm7n0iprepwx1hm94gwf FOREIGN KEY (users_id) REFERENCES users(id);


--
-- Name: run_attributes fkpgtba4py3rdxpoowrxuj2vplc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY run_attributes
    ADD CONSTRAINT fkpgtba4py3rdxpoowrxuj2vplc FOREIGN KEY (run_id) REFERENCES run(id);


--
-- Name: test_suite_assertions fkpig4kpl9sw0bkodan8ft8l2x9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_assertions
    ADD CONSTRAINT fkpig4kpl9sw0bkodan8ft8l2x9 FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);


--
-- Name: test_suite_headers fkq5uu037c4es0ol46rsdllibrm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_headers
    ADD CONSTRAINT fkq5uu037c4es0ol46rsdllibrm FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);


--
-- Name: test_suite_authors fkrf7urej9g5drn84a9uf4gxafa; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_suite_authors
    ADD CONSTRAINT fkrf7urej9g5drn84a9uf4gxafa FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);


--
-- Name: qrtz_blob_triggers qrtz_blob_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_blob_triggers
    ADD CONSTRAINT qrtz_blob_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_cron_triggers qrtz_cron_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_cron_triggers
    ADD CONSTRAINT qrtz_cron_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_simple_triggers qrtz_simple_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_simple_triggers
    ADD CONSTRAINT qrtz_simple_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_simprop_triggers qrtz_simprop_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_simprop_triggers
    ADD CONSTRAINT qrtz_simprop_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- Name: qrtz_triggers qrtz_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY qrtz_triggers
    ADD CONSTRAINT qrtz_triggers_sched_name_fkey FOREIGN KEY (sched_name, job_name, job_group) REFERENCES qrtz_job_details(sched_name, job_name, job_group);


--
-- PostgreSQL database dump complete
--
