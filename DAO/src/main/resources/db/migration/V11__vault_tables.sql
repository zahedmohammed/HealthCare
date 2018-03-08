--
-- Name: vault; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE vault (
    id character varying(255) NOT NULL,
    _key character varying(255) NOT NULL,
    val text NOT NULL,
    description text,

    visibility character varying(255),

    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    org_id character varying(255)
);

--
-- Name: vault org_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vault
    ADD CONSTRAINT vault_org_fk FOREIGN KEY (org_id) REFERENCES org(id);

--
-- Name: cluster vault_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vault
    ADD CONSTRAINT vault_pkey PRIMARY KEY (id);

