--
-- Name: skill; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE skill (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    _key character varying(255) NOT NULL,
    skill_type character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    org_id character varying(255)
);

--
-- Name: skill org_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_org_fk FOREIGN KEY (org_id) REFERENCES org(id);

--
-- Name: cluster skill_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_pkey PRIMARY KEY (id);

--
-- Name: skill_opts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE skill_opts (
    _order character varying(255),
    label character varying(255) NOT NULL,
    _value text,
    mandatory boolean NOT NULL,
    skill_id character varying(255)

);

--
-- Name: skill_opts skill_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY skill_opts
    ADD CONSTRAINT skill_opts_skill_fk FOREIGN KEY (skill_id) REFERENCES skill(id);

--
-- Name: skill; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE skill_subscription (
    name character varying(255) NOT NULL,
    description text,
    skill_type character varying(255) NOT NULL,
    skill_id character varying(255),
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    org_id character varying(255)
);

--
-- Name: skill_subscription org_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY skill_subscription
    ADD CONSTRAINT skill_subscription_org_fk FOREIGN KEY (org_id) REFERENCES org(id);

--
-- Name: skill_subscription skill_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY skill_subscription
    ADD CONSTRAINT skill_subscription_skill_fk FOREIGN KEY (skill_id) REFERENCES skill(id);

--
-- Name: cluster skill_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY skill_subscription
    ADD CONSTRAINT skill_subscription_pkey PRIMARY KEY (id);

--
-- Name: skill_subscription_opts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE skill_subscription_opts (
    _order character varying(255),
    label character varying(255) NOT NULL,
    _value text,
    mandatory boolean NOT NULL,
    skill_subscription_id character varying(255)

);

--
-- Name: skill_subscription_opts skill_subscription_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--
ALTER TABLE ONLY skill_subscription_opts
    ADD CONSTRAINT skill_subscription_opts_skill_subscription_fk FOREIGN KEY (skill_subscription_id) REFERENCES skill_subscription(id);