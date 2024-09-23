insert into user_table (id, name, date_of_birth, password)
VALUES (1, 'Alexei', '1994-01-11', '$2a$10$pQRy4fV19CkTddDw4dbid.YX.tHkWZ2KWKY8eznsVjsy5BH3hSUzS'), -- zxcvb098532
       (2, 'Roman', '1998-03-28', '$2a$10$y0XtHl3fMBa5fz/tK0jREe0.JSOeO3x/Hyxg2M1ZqUz8EK8YQcMkq'); -- zxcvb098

insert into account (id, user_id, balance, max_balance)
VALUES (1, 1, 100.0, 100.0 * 2.07),
       (2, 2, 200.0, 200.0 * 2.07);

insert into email_data (id, user_id, email)
values (1, 1, 'test@dot.com'),
       (2, 2, 'example@google.com');

insert into phone_data (id, user_id, phone)
values (1, 1, '792156589565'),
       (2, 2, '792156958536');
