CREATE TRIGGER `update_state_registration` BEFORE INSERT ON `checks`
 FOR EACH ROW UPDATE registrations r SET r.state='INVOICED' WHERE r.id_registration=NEW.registration_id_fk
