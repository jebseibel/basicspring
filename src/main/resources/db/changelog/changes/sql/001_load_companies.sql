--liquibase formatted sql

--changeset create_accounts:1
INSERT INTO company (
    id, extid, code, name, description,
    created_at, updated_at,
    deleted_at, active
) VALUES
      (101, '58f1f85a-aed6-4dea-b2a1-b87b1d158244', 'SOS', 'SOS Computers', 'Startup support and repairs',
       NOW(), NOW(), NULL, 1),
      (102, 'b8e132f3-eef1-4e7f-a6ee-387652e8ca80', 'TEC', 'TechEdge', 'Small business IT services',
       NOW(), NOW(), NULL, 1),
      (103, '6344e9a8-125f-4380-bdbc-3888b6f79f1f', 'GLO', 'GlobalNet', 'Enterprise networking',
       NOW(), NOW(), NULL, 1),
      (104, 'f122329d-21ee-4cec-ae54-d5ef4faa756f', 'HOM', 'HomeFix', 'Residential PC repair',
       NOW(), NOW(), NULL,  0),
      (105, 'f8bf43d5-517d-4707-9d4c-fda9c926976f', 'EDU', 'EduTech', 'Tech solutions for schools',
       NOW(), NOW(), NULL, 1);
