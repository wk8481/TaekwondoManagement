-- data.sql
-- users
INSERT INTO application_user(username, password, role) VALUES
                                                           ('wk8481','$2a$10$5bKeN9P53/tsYdSnkyQ/suXeaesAlAXdGKReULws.HwAOLhrza3bS', 0 /* user, password- zyxxx25wiwi */),
                                                           ('TheCEO', '$2a$10$ZhfQXUcVJ7Iw8hDlwVXvBO3Ba7tjk1VIVCgPpDalZ/0ZSqn8OFzrq', 1 /* ceo, password- ceo123 */);

-- Instructors
INSERT INTO instructors (title, name) VALUES
                                          ('Grand Master', 'Grand Master Splinter'),
                                          ('Sensei', 'Sensei Tom');


-- Students
INSERT INTO students (name, start_Date, instructor_id) VALUES
                                                           ('John Doe', '2023-01-01', 1),
                                                           ('Jane Smith', '2023-02-15', 2),
                                                           ('Bob Johnson', '2023-03-20', 1);

-- Techniques
INSERT INTO techniques (name, type, description, instructor_id) VALUES
                                                                    ('Dolly Chagi', 'KICK', 'Powerful kicking technique', 1),
                                                                    ('Arae Makki', 'BLOCK', 'Defensive blocking technique', 2),
                                                                    ('Momtong Jireugi', 'PUNCH', 'Basic punching technique', 1);

-- Linking Techniques to Students
INSERT INTO student_techniques (student_id, technique_id) VALUES
                                                             (1, 1), -- John Doe knows Roundhouse Kick
                                                             (1, 2), -- John Doe knows Block
                                                             (2, 1), -- Jane Smith knows Roundhouse Kick
                                                             (3, 3); -- Bob Johnson knows Punch
