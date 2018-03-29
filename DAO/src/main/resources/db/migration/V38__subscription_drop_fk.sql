--
-- Name: subscription_task drop fk
--


ALTER TABLE ONLY subscription_task DROP  CONSTRAINT IF EXISTS subscription_task_skill_subscription_fk;
ALTER TABLE subscription_task ALTER COLUMN subscription_id DROP NOT NULL;