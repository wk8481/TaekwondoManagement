-- data.sql

-- Instructors
-- Instructors
INSERT INTO INSTRUCTORS (TITLE, NAME) VALUES
                                              ('Grand Master', 'Grand Master Splinter'),
                                              ('Sensei', 'Sensei Tom');


-- Students
INSERT INTO STUDENTS (NAME, START_DATE, INSTRUCTOR_ID) VALUES
                                                           ('John Doe', '2023-01-01', 1),
                                                           ('Jane Smith', '2023-02-15', 2),
                                                           ('Bob Johnson', '2023-03-20', 1);

-- Techniques
INSERT INTO TECHNIQUES (NAME, TYPE, DESCRIPTION, INSTRUCTOR_ID) VALUES
                                                                    ('Dolly Chagi', 'KICK', 'Powerful kicking technique', 1),
                                                                    ('Arae Makki', 'BLOCK', 'Defensive blocking technique', 2),
                                                                    ('Momtong Jireugi', 'PUNCH', 'Basic punching technique', 1);

-- Linking Techniques to Students
INSERT INTO STUDENT_TECHNIQUE (STUDENT_ID, TECHNIQUE_ID) VALUES
                                                             (1, 1), -- John Doe knows Roundhouse Kick
                                                             (1, 2), -- John Doe knows Block
                                                             (2, 1), -- Jane Smith knows Roundhouse Kick
                                                             (3, 3); -- Bob Johnson knows Punch

