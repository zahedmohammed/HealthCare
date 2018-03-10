--
-- Name: subscription_task; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE subscription_task (
    id character varying(255) NOT NULL,

    subscription_id character varying(255) NOT NULL,
    logs text,

    _type character varying(255),
    _status character varying(255),
    _result character varying(255),

    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone
);

--
-- Name: vault subscription_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY subscription_task
    ADD CONSTRAINT subscription_task_skill_subscription_fk FOREIGN KEY (subscription_id) REFERENCES org(id);

--
-- Name: cluster subscription_task_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY subscription_task
    ADD CONSTRAINT subscription_task_pkey PRIMARY KEY (id);

