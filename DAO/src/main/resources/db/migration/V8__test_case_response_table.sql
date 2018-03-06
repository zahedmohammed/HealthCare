--
-- Name: test_case_response; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_case_response (
    id character varying(255) NOT NULL,

    name character varying(255) NOT NULL,
    description text,
    _key character varying(255) NOT NULL,
    skill_type character varying(255) NOT NULL,

    project character varying(255) NOT NULL,
    job character varying(255) NOT NULL,
    env character varying(255) NOT NULL,
    region character varying(255) NOT NULL,
    suite character varying(255) NOT NULL,
    testCase character varying(255) NOT NULL,
    endpoint text,
    endpointEval text,
    request text,
    requestEval text,
    response text,
    logs text,
    itKey character varying(255) NOT NULL,

    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone
);

--
-- Name: cluster test_case_response_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_case_response
    ADD CONSTRAINT test_case_response_pkey PRIMARY KEY (id);
