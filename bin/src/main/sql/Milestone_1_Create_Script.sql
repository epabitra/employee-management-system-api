/*!40101 SET NAMES utf8 */;

CREATE TABLE `department` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `name` VARCHAR(765),
    `description` VARCHAR(765),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `bank_account` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `user_id` BIGINT(20),
    `account_number` VARCHAR(765),
    `account_type` VARCHAR(765),
    `bank_name` VARCHAR(765),
    `ifsc_code` VARCHAR(765),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `holidays_list` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `name` VARCHAR(765),
    `description` VARCHAR(765),
    `holiday_date` DATE,
    `region` VARCHAR(765),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `roles` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `name` VARCHAR(765),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `team` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `team_name` VARCHAR(765),
    `description` VARCHAR(765),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `salary` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `user_id` BIGINT(20),
    `basic_salary` DOUBLE,
    `allowance` DOUBLE,
    `bonus` DOUBLE,
    `deductions` DOUBLE,
    `effective_from` DATE,
    `is_current` BIT(1),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `user_team` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `user_id` BIGINT(20),
    `team_id` BIGINT(20),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE users (
    id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    UUID VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    mobile_number VARCHAR(255),
    password_hash VARCHAR(255),
    image_url VARCHAR(255),
    gender VARCHAR(255),
    dob DATE,
    date_of_birth DATE,
    joining_date DATE,
    department VARCHAR(255),
    designation VARCHAR(255),
    ROLE VARCHAR(255),
    salary DOUBLE,
    timezone_id VARCHAR(255),
    lang_key VARCHAR(255),
    ACTIVE BIT(1),
    created_by VARCHAR(255),
    created_date DATETIME,
    updated_by VARCHAR(255),
    updated_date DATETIME
);


CREATE TABLE `user_department` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `user_id` BIGINT(20),
    `dept_id` BIGINT(20),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `user_role` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `user_id` BIGINT(20),
    `role_id` BIGINT(20),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `user_activity` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `user_id` BIGINT(20),
    `activity_type` VARCHAR(765),
    `activity_time` DATETIME,
    `description` VARCHAR(765),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);

CREATE TABLE `user_role_department` (
    `id` BIGINT(20),
    `uuid` VARCHAR(765),
    `user_id` BIGINT(20),
    `role_id` BIGINT(20),
    `department_id` BIGINT(20),
    `active` BIT(1),
    `created_by` VARCHAR(765),
    `created_date` DATETIME,
    `updated_by` VARCHAR(765),
    `updated_date` DATETIME
);
