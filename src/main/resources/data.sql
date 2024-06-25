DELETE FROM adoption;
DELETE FROM user_preference;
DELETE FROM preference_animal;
DELETE FROM preference;
DELETE FROM animal;
DELETE FROM user;

INSERT INTO user (id, first_name, last_name, email, phone_number, password) VALUES (1, 'John', 'Doe', 'john.doe@gmail.com', '677888555', 'password123');
INSERT INTO user (id, first_name, last_name, email, phone_number, password) VALUES (2, 'Hannah', 'Montana', 'hannah.montana@interia.com', '666333444', 'password321');
INSERT INTO user (id, first_name, last_name, email, phone_number, password) VALUES (3, 'Zbigniew', 'Nowak', 'zbysiu.nowak@gmail.com', '654333222', 'pass322');
INSERT INTO animal (id, type, name, age, color, status, short_description) VALUES (1, 'Dog', 'Buddy', 3, 'Brown', 'Available', 'Friendly dog');
INSERT INTO animal (id, type, name, age, color, status, short_description) VALUES (2, 'Dog', 'Lily', 1, 'White', 'Available', 'Friendly dog');
INSERT INTO animal (id, type, name, age, color, status, short_description) VALUES (3, 'Cat', 'Laila', 5, 'Gray', 'Available', 'Very friendly');
INSERT INTO animal (id, type, name, age, color, status, short_description) VALUES (4, 'Cat', 'Tasha', 6, 'Black', 'Available', 'Purrs loudly');
INSERT INTO animal (id, type, name, age, color, status, short_description) VALUES (5, 'Parrot', 'Mimi', 3, 'Colorful', 'Available', 'knows many words, doesn''t swear');
INSERT INTO preference (id, type, color, min_age, max_age) VALUES (1, 'Dog', 'Brown', 1, 5);
INSERT INTO preference (id, type, color, min_age, max_age) VALUES (2, 'Dog', 'Brown', 1, 5);
INSERT INTO adoption (id, adoption_date, user_id, animal_id) VALUES (1, '2024-06-25', 1, 1);
