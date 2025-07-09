ALTER TABLE patients ADD active tinyint;

UPDATE patients SET active = 1