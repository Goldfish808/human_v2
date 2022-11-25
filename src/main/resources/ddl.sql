DROP TABLE  if exists user;
DROP TABLE  if exists category;
DROP TABLE  if exists company;
DROP TABLE  if exists resume;
DROP TABLE  if exists recruit;
DROP TABLE  if exists apply;
DROP TABLE  if exists subscribe;

CREATE TABLE user (
	user_id INT auto_increment PRIMARY KEY,
	username VARCHAR(50) UNIQUE NOT null,
	password VARCHAR(500) NOT null,
	name VARCHAR(50) NOT null,
	email VARCHAR(120) UNIQUE NOT null,
	phone_number VARCHAR(100) UNIQUE,
	created_at TIMESTAMP
) engine=InnoDB default charset=UTF8;

-- 분야
CREATE TABLE category (
	category_id INT auto_increment PRIMARY KEY,
	category_resume_id INT,
	category_recruit_id INT,
	category_name VARCHAR(50)
) engine=InnoDB default charset=UTF8;

-- 회사
CREATE TABLE company (
	company_id INT auto_increment PRIMARY KEY,
	company_username VARCHAR(50) UNIQUE NOT null,
	company_password VARCHAR(500) NOT null,
	company_name VARCHAR(50) UNIQUE NOT null,
	company_email VARCHAR(120) UNIQUE NOT null,
	company_phone_number VARCHAR(100) UNIQUE,
	company_address VARCHAR(150) NOT null,
	company_logo VARCHAR(500),
	company_created_at TimeStamp
) engine=InnoDB default charset=UTF8;

-- 이력서
CREATE TABLE resume(
	resume_id INT AUTO_INCREMENT PRIMARY KEY,
	resume_title VARCHAR(50) NOT null,
	resume_photo VARCHAR(500),
	resume_education VARCHAR(50),
	resume_career VARCHAR(50),
	resume_link VARCHAR(500),
	resume_read_count INT,
	resume_user_id INT,
	resume_created_at TIMESTAMP
) engine=InnoDB default charset=UTF8;


-- 채용 공고
CREATE TABLE recruit(
	recruit_id int auto_increment PRIMARY KEY,
	recruit_title VARCHAR(50) NOT NULL,
	recruit_career VARCHAR(50),
	recruit_salary INT,
	recruit_location VARCHAR(120),
	recruit_content LONGTEXT,
	recruit_read_count INT,
	recruit_company_id INT,
	recruit_deadline TIMESTAMP,
	recruit_created_at TIMESTAMP
) engine=InnoDB default charset=UTF8;

-- 채용 지원
CREATE TABLE apply(
	apply_id INT AUTO_INCREMENT PRIMARY KEY,
	apply_recruit_id INT,
	apply_resume_id INT,
	apply_created_at TIMESTAMP
) engine=InnoDB default charset=UTF8;

-- 관심 기업 구독
CREATE TABLE subscribe(
	subscribe_id INT auto_increment PRIMARY KEY,
	subscribe_user_id INT,
	subscribe_company_id INT,
	subscribe_created_at TIMESTAMP
) engine=InnoDB default charset=UTF8;