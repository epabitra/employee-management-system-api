-- Admin User Creation Script
INSERT INTO users (id, UUID, first_name, last_name, email, phone, mobile_number,
  password_hash, image_url, gender, dob, date_of_birth, joining_date,
  department, designation, ROLE, salary, timezone_id, lang_key,
  ACTIVE, created_by, created_date, updated_by, updated_date)
SELECT IFNULL((SELECT MAX(id) FROM users), 0) + 1, UUID(), 'Admin', 'User', 'admin@gmail.com',
'1234567890', '9876543210', 'admin@123', 'https://example.com/img.jpg', 'Male',
'1990-01-01', '1990-01-01', CURDATE(), 'IT', 'Manager', 'Admin', 100000.00,
'Asia/Kolkata', 'en', 1, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@gmail.com');

-- Roles Insertion Script
-- Admin
INSERT INTO roles (id, UUID, NAME, ACTIVE, created_by, created_date, updated_by, updated_date)
SELECT IFNULL((SELECT MAX(id) FROM roles), 0) + 1, UUID(), 'Admin', 1,
u.id, NOW(), u.id, NOW()
FROM users u
WHERE u.email = 'admin@gmail.com'
AND NOT EXISTS (SELECT 1 FROM roles WHERE NAME = 'Admin');

-- Employee
INSERT INTO roles (id, UUID, NAME, ACTIVE, created_by, created_date, updated_by, updated_date)
SELECT IFNULL((SELECT MAX(id) FROM roles), 0) + 1, UUID(), 'Employee', 1,
u.id, NOW(), u.id, NOW()
FROM users u
WHERE u.email = 'admin@gmail.com'
AND NOT EXISTS (SELECT 1 FROM roles WHERE NAME = 'Employee');

-- Department Insertion Script
-- IT Department
INSERT INTO department (id, UUID, NAME, DESCRIPTION, ACTIVE, created_by, created_date, updated_by, updated_date)
SELECT IFNULL((SELECT MAX(id) FROM department), 0) + 1, UUID(), 'IT Department', 'Handles tech and infrastructure', 1,
id, NOW(), id, NOW()
FROM users
WHERE email = 'admin@gmail.com' AND NOT EXISTS (SELECT 1 FROM department WHERE NAME = 'IT Department');

-- UserRoleDepartment Insertion Script
INSERT INTO user_role_department (id, UUID, user_id, role_id, department_id, ACTIVE, created_by, created_date, updated_by, updated_date)
SELECT IFNULL((SELECT MAX(id) FROM user_role_department), 0) + 1, UUID(), u.id, r.id, d.id, 1, u.id, NOW(), u.id, NOW()
FROM users u, roles r, department d
WHERE u.email = 'admin@gmail.com' AND r.NAME = 'Employee' AND d.NAME = 'IT Department'
AND NOT EXISTS (
  SELECT 1 FROM user_role_department urd
  WHERE urd.user_id = u.id AND urd.role_id = r.id AND urd.department_id = d.id
);
