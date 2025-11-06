-- Test users for development/testing
-- Password for all test users: "password123"
-- BCrypt hash generated with strength 10

INSERT INTO users (
    id, extid, username, password, email, role,
    created_at, updated_at, deleted_at, active
) VALUES
    (1, 'user-001', 'testuser', '$2a$10$N9qo8uLOickgx2ZMRZoMye0J2Ky3sVPHQVUq5N2k1HQwJLEWJBZOC', 'test@example.com', 'USER',
     NOW(), NOW(), NULL, 1),
    (2, 'user-002', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye0J2Ky3sVPHQVUq5N2k1HQwJLEWJBZOC', 'admin@example.com', 'ADMIN',
     NOW(), NOW(), NULL, 1),
    (3, 'user-003', 'jeb', '$2a$10$N9qo8uLOickgx2ZMRZoMye0J2Ky3sVPHQVUq5N2k1HQwJLEWJBZOC', 'jeb@example.com', 'ADMIN',
     NOW(), NOW(), NULL, 1);
