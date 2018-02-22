--
-- Name: suite response; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE suite (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,

    run_id character varying(255),
    suite_name character varying(255),

    tests bigint,
    failed bigint,
    size_ bigint,
    time_ bigint
);

--
-- Name: suite suite_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY suite
    ADD CONSTRAINT suite_pkey PRIMARY KEY (id);