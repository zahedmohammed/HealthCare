-- Author: Zahed
--
-- Table: Primary_Account, Recent_Transcation
-- Column: user_id
--

alter table primary_account drop column if exists user_id;
alter table recent_transaction drop column if exists user_id;