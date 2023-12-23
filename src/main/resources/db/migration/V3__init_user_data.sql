insert into users (id, created_date, description, email, name, password, roles, surname, update_date, username)
values (
        nextVal('hibernate_sequence'),
        current_timestamp,
        '',
        'admin@admin.com',
        'Admin',
        'admin',
        'ROLE_ADMIN',
        'Admin',
        current_timestamp,
        'admin'
        );