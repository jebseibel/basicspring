INSERT INTO profile (
    id, extid, nickname, fullname,
    created_at, updated_at, deleted_at, active
) VALUES
      (101, '12d3ff27-1fda-49b2-b864-d63a74bc640e', 'jeb', 'John E. Brown',
       NOW(), NOW(), NULL, 1),
      (102, '351ceb47-2eaf-4f41-913a-6ea6696942d5', 'ally', 'Allison Green',
       NOW(), NOW(), NULL, 1),
      (103, '8966479b-323b-46dc-8a8d-635029b05574', 'mike', 'Michael Ortega',
       NOW(), NOW(), NULL, 1),
      (104, '28cc956d-5911-4f80-be7c-e20a8554d460', 'sara', 'Sara Patel',
       NOW(), NOW(), NULL, 1),
      (105, 'bce71efd-b5da-4a58-b514-e2da834112e2', 'dev', 'Devon Lee',
       NOW(), NOW(), NULL, 1);
