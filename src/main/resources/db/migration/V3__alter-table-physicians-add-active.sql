ALTER TABLE physicians ADD active tinyint;

UPDATE physicians SET active = 1