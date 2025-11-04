SET search_path TO public;

CREATE TABLE address
(
    id       BIGSERIAL PRIMARY KEY,
    street   VARCHAR(150),
    city     VARCHAR(100),
    zip_code VARCHAR(20),
    country  VARCHAR(100)
);

ALTER TABLE address
    ADD CONSTRAINT unique_address UNIQUE (street, city, zip_code, country);

CREATE TABLE contact_info
(
    id           BIGSERIAL PRIMARY KEY,
    email        VARCHAR(100) NOT NULL UNIQUE, -- enforce uniqueness if each email must be distinct
    phone_number VARCHAR(20),
    address_id   BIGINT       NOT NULL,        -- make NOT NULL if every contact must have an address
    CONSTRAINT fk_contact_address
        FOREIGN KEY (address_id)
            REFERENCES address (id)
            ON DELETE CASCADE                  -- optional: delete contact_info if its address is deleted
);

CREATE TABLE academic_info
(
    id              BIGSERIAL PRIMARY KEY,
    enrollment_date DATE        NOT NULL,
    program         VARCHAR(100),
    department      VARCHAR(100),
    year_level      INTEGER,
    status          VARCHAR(32) NOT NULL CHECK (
        status IN (
                   'APPLICANT', 'ADMITTED', 'ENROLLED', 'LEAVE_OF_ABSENCE',
                   'PROBATION', 'SUSPENDED', 'WITHDRAWN', 'GRADUATED', 'DISMISSED'
            )
        ),
    gpa             NUMERIC(3, 2),
    student_id BIGINT,
    CONSTRAINT academic_info_student_id_key UNIQUE(student_id)
);

CREATE TABLE student
(
    student_id       BIGSERIAL PRIMARY KEY,
    student_number   VARCHAR(50) UNIQUE NOT NULL,
    first_name       VARCHAR(100)       NOT NULL,
    last_name        VARCHAR(100)       NOT NULL,
    date_of_birth    DATE               NOT NULL,
    gender           VARCHAR(20)        NOT NULL CHECK (
        gender IN ('MALE', 'FEMALE')
        ),
    contact_info_id  BIGINT REFERENCES contact_info (id),
    --academic_info_id BIGINT UNIQUE REFERENCES academic_info (id),
    created_at       TIMESTAMP DEFAULT NOW(),
    updated_at       TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_ (
                       id BIGSERIAL PRIMARY KEY,
                       username_ VARCHAR(150),
                       password_ VARCHAR(150),
                       role_ VARCHAR(20)
);

/*
SELECT *FROM academic_info;
ALTER TABLE contact_info
	ADD CONSTRAINT contact_info_email_uk 
	UNIQUE (email);
ALTER TABLE student
  ALTER COLUMN created_at SET DEFAULT now(),
  ALTER COLUMN updated_at SET DEFAULT now();
*/

/*
SELECT *
From student;
SELECT *
FROM address;
SELECT *
FROM academic_info;
SELECT *
FROM contact_info;

SELECT s.student_number,
       s.first_name,
       s.last_name,
       s.date_of_birth,
       c.email,
       c.phone_number,
       ad.street,
       ad.zip_code,
       ad.city,
       ad.country,
       ac.program,
       ac.department,
       ac.year_level,
       ac.enrollment_date,
       ac.status,
       ac.gpa
FROM student s
         JOIN contact_info c ON s.contact_info_id = c.id
         JOIN address ad ON c.address_id = ad.id
         JOIN academic_info ac ON s.academic_info_id = ac.id
WHERE s.student_number = 'S2025001';
*/
/*
DROP TABLE student;
DROP TABLE address;
DROP TABLE contact_info;
DROP TABLE academic_info;
*/










