INSERT INTO user (id, username, first_name, last_name, email, phone_number, password, role)
VALUES (1, 'user1', 'John', 'Doe', 'john.doe@example.com', '123456789', 'password', 'USER'),
       (2, 'user2', 'Jane', 'Doe', 'jane.doe@example.com', '987654321', 'password', 'USER');

INSERT INTO animal (id, name, species, breed, age, gender)
VALUES (1, 'Buddy', 'Dog', 'Labrador', 3, 'Male'),
       (2, 'Mittens', 'Cat', 'Siamese', 2, 'Female');

INSERT INTO adoption (id, adoption_date, user_id, animal_id)
VALUES (1, '2024-07-10', 1, 1),
       (2, '2024-07-11', 2, 1);
