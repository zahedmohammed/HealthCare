--
-- Name: users users_email; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_email UNIQUE (email);


--
-- Name: org org_name; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY org
    ADD CONSTRAINT org_name UNIQUE (name);