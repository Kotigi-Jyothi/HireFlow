INSERT INTO companies
(id,name,industry,size,logo_path)
VALUES
(1,'HireFlow Pvt Ltd','IT','100-500','logo.png');


INSERT INTO users(id,name,email,password,role,is_active,created_at,company_id)VALUES
(1,'Jyothi','kotigijyothi1206@gmail.com','123456','CANDIDATE',true,NOW(),1),

(2,'Riya','kotig333@gmail.com','123456','CANDIDATE',true,NOW(),1),

(3,'Admin','admin@gmail.com','123456','ADMIN',true,NOW(),1),

(4,'HR Manager','kotigi333@gmail.com','123456','HR_MANAGER',true,NOW(),1),

(5,'Interviewer','interviewer1@gmail.com','123456','INTERVIEWER',true,NOW(),1),

(6,'Interviewer','interviewer2@gmail.com','123456','INTERVIEWER',true,NOW(),1);

INSERT INTO pipeline_stages
(id, stage_name, sequence_order, stage_type, sla_days, auto_email_template, company_id)
VALUES
(1,'Applied',1,'APPLIED',1,NULL,1),
(2,'Screening',2,'SCREENING',2,NULL,1),
(3,'Technical',3,'TECHNICAL',5,NULL,1),
(4,'HR Round',4,'HR',2,NULL,1),
(5,'Offer',5,'OFFER',1,NULL,1),
(6,'Hired',6,'HIRED',1,NULL,1);


INSERT INTO jobs(id,title,department,location,type,experience_required,skills_required,
salary_min,salary_max,openings_count,status,company_id,created_by) 
VALUES 
(1,'Java Developer','Engineering','Hyderabad','Full Time',2,
'["Java","Spring Boot","MySQL"]',400000,800000,3,'PUBLISHED',1,3),

(2,'web Developer','Engineering','Hyderabad','Full Time',2,
'["Java","Spring Boot","MySQL"]',400000,800000,3,'PUBLISHED',1,4);

INSERT INTO applications(id,applied_at,resume_path,overall_score,
status,last_stage_changed_at,job_id,candidate_id,current_stage_id,sla_breached) 
VALUES 
(1,'2026-05-30 10:00:00.000000','resume.pdf',8.5,'IN_PROGRESS','2026-06-20 10:00:00.000000',1,1,1,false),
(2,"2026-05-31 10:00:00.000000",'resume.pdf',8.5,'IN_PROGRESS','2026-06-21 10:00:00.000000',1,2,1,false),
(3,"2026-05-30 10:00:00.000000",'resume.pdf',8.5,'IN_PROGRESS','2026-06-22 10:00:00.000000',2,2,1,false);




INSERT INTO interviews(id,scheduled_at,duration_minutes,status,interview_type,application_id,interviewer_id,stage_id) VALUES
(1,"2026-06-20T10:10:00.924714",60,'SCHEDULED','ONLINE',1,4,1),
(2,"2026-06-21T10:11:00.924714",45,'SCHEDULED','OFFLINE',1,4,2);

/*INSERT INTO offer_letters(id,content,pdf_path,status,signature_token,sent_at,signed_at,application_id) VALUES
(1,'{"position":"Java Developer","salary":"700000"}','SENT','TOKEN123',NOW(),NULL,1);
*/

INSERT INTO stage_history
(id, changed_at, notes, application_id, from_stage, to_stage, changed_by)
VALUES
(1,'2026-06-01 09:00:00','Moved to Screening',1,1,2,2),
(2,'2026-06-03 09:00:00','Moved to Technical',1,2,3,2),

(3,'2026-06-02 10:00:00','Moved to Screening',2,1,2,2),

(4,'2026-06-01 11:00:00','Moved to Screening',3,1,2,2),
(5,'2026-06-04 11:00:00','Moved to Technical',3,2,3,2),
(6,'2026-06-07 11:00:00','Moved to HR Round',3,3,4,2);

INSERT INTO scorecard_templates(id,name,criteria,company_id) VALUES
(1,'Java Backend Template','{"Java":4,"Spring Boot":3,"MySQL":3}',1),
(2,'Frontend Template','{"HTML":2,"CSS":2,"JavaScript":6}',1);


INSERT INTO scorecard_submissions(id,scores,overall_rating,recommendation,strengths,weaknesses,submitted_at,interview_id,interviewer_id) VALUES
(1,'{"Java":9,"Spring Boot":8,"MySQL":9}',8.7,'HIRE','Strong coding skills','Needs improvement in optimization',"2026-06-20T10:19:43.924714",1,4);
/*(2,'{"Java":10,"Spring Boot":9,"MySQL":10}',9.7,'STRONG_HIRE','Excellent technical knowledge','Minor communication issues',NOW(),2,4);*/


INSERT INTO email_log(id,recipient,subject,template_used,sent_at,status,application_id) VALUES
(1,'kotigijyothi1206@gmail.com','Interview Invitation','INTERVIEW_INVITATION',NOW(),'SENT',1),
(2,'kotigi333@gmail.com','Offer Letter','OFFER_LETTER',NOW(),'SENT',1);
