
--
-- Name: subscription_task; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY subscription_task
    DROP CONSTRAINT subscription_task_skill_subscription_fk;

ALTER TABLE ONLY subscription_task
    ADD CONSTRAINT subscription_task_skill_subscription_fk FOREIGN KEY (subscription_id) REFERENCES skill_subscription(id);
