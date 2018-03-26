

--
-- Name: vault vault_org_key_uk
--

ALTER TABLE ONLY vault
    ADD CONSTRAINT vault_org_key_uk UNIQUE (org_id, _key)

