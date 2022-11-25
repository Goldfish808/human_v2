-- user 더미 데이터
insert into user(username, password, name, email, phone_number, created_at)
VALUES ('ssar', '1234', '장씨', 'wkdTl@naver.com', '01011112222', NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ('cos', '1234', '정씨', 'wjdTl@naver.com', '01022223333', NOW());
insert into user(username, password, name, email, phone_number, created_at)
VALUES ('sun', '1234', '김씨', 'rlaTl@naver.com', '01033332222', NOW());

-- company 더미 데이터
INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES('adt', '1234', 'ADT', 'adt@naver.com', '01020203060', '부산진구청', 'ADT.png', NOW());
INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES('gsbuilt', '1234', 'GS건설', 'gs@never.com', '01025259999', '광주광역시청', 'GS건설.png', NOW());
INSERT INTO company(company_username, company_password, company_name, company_email,
            company_phone_number, company_address, company_logo,   company_created_at)
VALUES('ktds', '1234', 'KT다이노소얼', 'kt_ds@never.com', '01088259999', '대구광역시청', 'kt_ds.png', NOW());

-- resume 더미 데이터
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES('이력서1', '고졸', '신입', 'defaultProfile.jpeg','https://github.com/Sylar0012',  1,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES('이력서2', '2년제대학졸업', '3년이상 ~ 5년미만', 'defaultProfile.jpeg','https://github.com/Sylar0012',  1,0, NOW());
INSERT INTO resume(resume_title, resume_education, resume_career, resume_photo,resume_link, resume_user_id, resume_read_count, resume_created_at)
VALUES('이력서3', '2년제대학졸업', '6년이상', 'defaultProfile.jpeg','https://github.com/Sylar0012',  2,0, NOW());

--resume용 category
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, null, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) values (1, NULL, 'Python');

-- recruit용 category
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'Flutter');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'Java');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'HTML&CSS');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'JavaScript');
insert into category(category_resume_id, category_recruit_id, category_name) values (NULL, 1, 'Python');

-- recruit 더미데이터
INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES('채용공고1', '고졸', 2400 , '부산 동구 망양로945번길 19 (범일동)', '1', 0, 1, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES('채용공고2', '2년제대학졸업', 3200 , '부산 동구 망양로945번길 19 (범일동)', '2', 0, 2, NOW(), NOW());

INSERT INTO recruit(recruit_title, recruit_career, recruit_salary, recruit_location, recruit_content, recruit_read_count, recruit_company_id, recruit_deadline, recruit_created_at)
VALUES('채용공고3', '3년제대학졸업', 3600 , '부산 동구 망양로945번길 19 (범일동)', '3', 0, 3, NOW(), NOW());


--subscribe 더미 데이터
insert into subscribe(subscribe_user_id, subscribe_company_id, subscribe_created_at) 
values (1, 1, now());
insert into subscribe(subscribe_user_id, subscribe_company_id, subscribe_created_at) 
values (1, 2, now());

--apply 더미 데이터
insert into apply(apply_recruit_id, apply_resume_id, apply_created_at)
values(1, 1, now());
insert into apply(apply_recruit_id, apply_resume_id, apply_created_at)
values(1, 2, now());
