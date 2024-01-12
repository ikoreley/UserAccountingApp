insert into users (created_date, description, email, name, password, roles, surname, update_date, username)
values (

        current_timestamp,
        '',
        'admin@admin.com',
        'Admin',
        '$2a$12$zFgENfvShvXOJeIIXMZ3MenszrfMiIIBENPFmGWRpAErZ2ClTQlum',
        'ROLE_ADMIN',
        'Admin',
        current_timestamp,
        'admin'
        );