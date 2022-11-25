# 미니프로젝트 humancloud2 - Rest하게 서버 만들기

### 개요

- Controller는 RestController로 변경
- Service의 모든 메서드에는 @Transactional 작성
- 각 도메인의 ReqDto와 RespDto 내에서 static으로 세부 Dto 만들어서 사용
- mybatis 경로 주의
- Exception은 throw new RuntimeException("필요한 메세지")로 작성
- Controller의 경로는 인증이 필요한 메서드들은 "/s"를 앞에 붙이고, 그 이외의 것들은 "/도메인/\*\*" 방식으로 작성
- Entity 생성자에는 @Builder 작성
- toEntity()는 Dto마다 작성, 관련 Dto 들도 각각 Dto 내에서 작성해서 사용
- insert는 SaveDto, update는 UpdateDto로 작성. 리스트를 보여주는 Dto는 도메인AllRespDto로 작성
- List 객체들의 변수명은 "도메인명List"로 작성

### DB - user생성 및 권한 부여

```sql
CREATE USER 'human'@'%' IDENTIFIED BY 'human1234';
CREATE database humandb;
GRANT ALL PRIVILEGES ON *.* TO 'human'@'%';
```

### 테이블 생성

```sql
-- 구직자(유저)
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
```

### fk 제약조건

```sql
-- 이력서 테이블 fk
ALTER TABLE resume ADD FOREIGN KEY(resume_user_id) REFERENCES user(user_id);

-- 채용 공고 테이블 fk
ALTER TABLE recruit ADD FOREIGN KEY(recruit_company_id) REFERENCES company(company_id);

-- 채용 지원 테이블 fk
ALTER TABLE apply ADD FOREIGN KEY(apply_recruit_id) REFERENCES recruit(recruit_id);
ALTER TABLE apply ADD FOREIGN KEY(apply_resume_id) REFERENCES resume(resume_id);

-- 관심 기업 구독 테이블 fk
ALTER TABLE comment ADD FOREIGN KEY(subscribe_user_id) REFERENCES user(user_id);
ALTER TABLE comment ADD FOREIGN KEY(subscribe_company_id) REFERENCES company(company_id);

```

### utf-8 변경

```sql
alter table user convert to character set utf8;

alter table company convert to character set utf8;

alter table resume convert to character set utf8;

alter table recruit convert to character set utf8;

alter table category convert to character set utf8;

alter table apply convert to character set utf8;

alter table subscribe convert to character set utf8;
```

### 더미 데이터

```sql
-- user 더미 데이터
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("ssar", "1234", "장씨", "wkdTl@naver.com", "01011112222", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("cos", "1234", "정씨", "wjdTl@naver.com", "01022223333", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("sun", "1234", "김씨", "rlaTl@naver.com", "01033332222", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("jin", "1234", "허씨", "jinTl@naver.com", "01044442222", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("cool", "1234", "노씨", "NoTl@naver.com", "01055552222", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("hotguy", "1234", "핫씨", "HOTTl@naver.com", "01018188888", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("hermes", "1234", "메씨", "hermes@naver.com", "01078790053", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("yun", "1234", "윤씨", "seock@naver.com", "01098746321", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("bts", "1234", "방씨", "tancream@naver.com", "01088772233", NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ("herry", "1234", "윙가디움레비오싸씨", "porter@naver.com", "01044444444", NOW());

-- company 더미 데이터
INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("adt", "1234", "ADT", "adt@never.com", "01020203060", "부산진구청", "ADT.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("gsbuilt", "1234", "GS건설", "gs@never.com", "01025259999", "광주광역시청", "GS건설.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("ktds", "1234", "KT다이노소얼", "kt_ds@never.com", "01088259999", "대구광역시청", "kt_ds.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("sgc", "1234", "(주)SGC", "SGC@never.com", "01080569999", "인천공항", "SGC.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("naver", "1234", "네이버", "네이버@never.com", "01078964123", "잠실역", "네이버.jpg", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("dbcar", "1234", "동부 자동차 손해보험", "dbcar@never.com", "010555584123", "G7 레지던스", "동부 자동차 손해보험.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("dongwon", "1234", "동원그룹", "동원그룹@never.com", "010555546423", "사직야구장", "동원그룹.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("ssg", "1234", "신세계 푸드", "신세계 푸드@never.com", "010623143123", "센텀시티 신세계백화점", "신세계 푸드.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("yogiyo", "1234", "요기요", "요기요@never.com", "010797184123", "SM엔터테인먼트", "요기요.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("woah", "1234", "우아한 청년들", "우아한 청년들@never.com", "01079444123", "용산역", "우아한 청년들.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("yosin", "1234", "유신", "유신@never.com", "010666184123", "부산역", "유신.png", NOW());

INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("jumpit", "1234", "점핏", "점핏@never.com", "010889984123", "남산타워", "점핏.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("koreabio", "1234", "코리아 바이오 협회", "koreaBio@never.com", "01082184123", "짚신", "코리아 바이오 협회.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("coupang", "1234", "쿠팡", "쿠팡@never.com", "010989884123", "양산시 물금역", "쿠팡.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("toss", "1234", "토스", "toss@never.com", "010444484123", "국민은행", "토스.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("posco", "1234", "포스코플로우", "posco@never.com", "010400684123", "포항역", "포스코플로우.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("korealand", "1234", "한국 토지 신탁", "koreaLand@never.com", "010998884123", "남포역", "한국 토지 신탁.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("hyundaidep", "1234", "현대 백화점 그룹", "hd@never.com", "010797184523", "현대 백화점", "현대 백화점 그룹.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("hyundai", "1234", "현대", "hytund@never.com", "010796684123", "현대 백화점", "현대.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("hmobis", "1234", "현대모비스", "hmobis@never.com", "010797180000", "현대 자동차", "현대모비스.png", NOW());


INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES("welding", "1234", "현대종합금속", "hwelding@never.com", "010696984123", "울산 현대", "현대종합금속.png", NOW());


-- resume 더미 데이터
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서1", "고졸", "신입", "defaultProfile.jpeg","https://github.com/Sylar0012",  1,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서2", "2년제대학졸업", "3년이상 ~ 5년미만", "defaultProfile.jpeg","https://github.com/Sylar0012",  1,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서3", "2년제대학졸업", "6년이상", "defaultProfile.jpeg","https://github.com/Sylar0012",  2,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서4", "2년제대학졸업", "3년이상 ~ 5년미만", "defaultProfile.jpeg","https://github.com/Sylar0012",  2,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서5", "대학원졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/Sylar0012",  3,0, NOW());

INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서6", "대학원졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/Goldfish808",  3,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서7", "고졸", "3년이상 ~ 5년미만", "defaultProfile.jpeg","https://github.com/Goldfish808",  4,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서8", "2년제대학졸업", "신입", "defaultProfile.jpeg","https://github.com/Goldfish808",  4,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서9", "3년제대학졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/Goldfish808",  5,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서10", "4년제대학졸업", "신입", "defaultProfile.jpeg","https://github.com/Goldfish808",  5,0, NOW());

INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서11", "4년제대학졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/gitthathonor",  6,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서12", "대학원졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/gitthathonor",  6,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서13", "2년제대학졸업", "3년이상 ~ 5년미만", "defaultProfile.jpeg","https://github.com/gitthathonor",  7,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서14", "3년제대학졸업", "신입", "defaultProfile.jpeg","https://github.com/gitthathonor",  7,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서15", "고졸", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/gitthathonor",  8,0, NOW());

INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서16", "대학원졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/yujin9907",  8,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서17", "고졸", "3년이상 ~ 5년미만", "defaultProfile.jpeg","https://github.com/yujin9907",  9,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서18", "3년제대학졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/yujin9907",  9,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서19", "4년제대학졸업", "신입", "defaultProfile.jpeg","https://github.com/yujin9907",  10,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES("이력서20", "2년제대학졸업", "1년이상 ~ 3년미만", "defaultProfile.jpeg","https://github.com/yujin9907",  10,0, NOW());

--resume용 category
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (2, null, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (2, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (2, null, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (3, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (3, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (3, NULL, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (4, NULL, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (5, null, 'Python');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (5, NULL, 'Flutter');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (6, null, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (6, NULL, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (7, NULL, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (7, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (7, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (7, NULL, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (8, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (8, NULL, 'Java');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (9, null, 'Flutter');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (10, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (10, null, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (11, NULL, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (11, null, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (11, NULL, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (12, null, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (12, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (12, null, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (13, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (13, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (13, NULL, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (14, null, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (15, null, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (15, null, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (15, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (15, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (15, NULL, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (16, null, 'Java');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (17, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (17, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (17, NULL, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (18, null, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (18, NULL, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (19, null, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (20, NULL, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (20, null, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (20, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (20, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (20, NULL, 'Python');

-- recruit 더미데이터
INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고1", "고졸", 2400 , "부산 동구 망양로945번길 19 (범일동)", "1", 0, 1, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고2", "2년제대학졸업", 3200 , "부산 동구 망양로945번길 19 (범일동)", "2", 0, 2, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고3", "3년제대학졸업", 3600 , "부산 동구 망양로945번길 19 (범일동)", "3", 0, 3, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고4", "4년제대학졸업", 4000 , "부산 동구 망양로945번길 19 (범일동)", "4", 0, 4, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고5", "고졸", 2400 , "부산 동구 망양로945번길 19 (범일동)", "5", 0, 5, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고6", "2년제대학졸업", 3200 , "부산 동구 망양로945번길 19 (범일동)", "6", 0, 6, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고7", "대학원졸업", 3600 , "부산 동구 망양로945번길 19 (범일동)", "7", 0, 7, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고8", "4년제대학졸업", 4000 , "부산 동구 망양로945번길 19 (범일동)", "8", 0, 8, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고9", "고졸", 2400 , "부산 동구 망양로945번길 19 (범일동)", "9", 0, 9, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고10", "2년제대학졸업", 3200 , "부산 동구 망양로945번길 19 (범일동)", "10", 0, 10, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고11", "대학원졸업", 3600 , "부산 동구 망양로945번길 19 (범일동)", "11", 0, 11, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고12", "2년제대학졸업", 4000 , "부산 동구 망양로945번길 19 (범일동)", "12", 12, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고13", "고졸", 2400 , "부산 동구 망양로945번길 19 (범일동)", "13", 0, 13, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고14", "대학원졸업", 3200 , "부산 동구 망양로945번길 19 (범일동)", "14", 0, 14, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고15", "3년제대학졸업", 3600 , "부산 동구 망양로945번길 19 (범일동)", "15", 0, 15, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고16", "대학원졸업", 4000 , "부산 동구 망양로945번길 19 (범일동)", "16", 0, 16, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고17", "고졸", 2400 , "부산 동구 망양로945번길 19 (범일동)", "17", 0, 17, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고18", "고졸", 3200 , "부산 동구 망양로945번길 19 (범일동)", "18", 0, 18, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고19", "4년제대학졸업", 3600 , "부산 동구 망양로945번길 19 (범일동)", "19", 0, 19, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고20", "3년제대학졸업", 4000 , "부산 동구 망양로945번길 19 (범일동)", "20", 0, 20, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES("채용공고21", "2년제대학졸업", 4000 , "부산 동구 망양로945번길 19 (범일동)", "21", 0, 21, NOW(), NOW());

-- recruit용 category
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 2, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 2, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 2, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 3, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 3, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 3, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 4, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 5, 'Python');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 5, 'Flutter');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 6, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 6, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 7, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 7, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 7, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 7, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 8, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 8, 'Java');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 9, 'Flutter');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 10, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 10, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 11, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 11, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 11, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 12, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 12, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 12, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 13, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 13, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 13, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 14, 'JavaScript');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 15, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 15, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 15, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 15, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 15, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 16, 'Java');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 17, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 17, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 17, 'Python');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 18, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 18, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 19, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 20, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 20, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 20, 'HTML&CSS');

insert into category(category_resume_id, category_recruit_id, category_name) VALUES (null, 21, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) VALUES (NULL, 21, 'Python');
```

### recruit-detail 수정사항 메모

PathVarilable 이 해당 프로젝트에서는 동작하지않아, 쿼리스트릥으로
Get 요청을 받아 findById 메서드 실행을 한 후,
jstl 로 view 페이지에 로드 했음

layout/header.jsp 에서 충돌나는 헤드 코드 saveForm 에서만 쓰일 수 있도록 코드 옮김. 이 전의 코드를 참고.
