CREATE TABLE student(
	student_id BIGSERIAL PRIMARY KEY,
	student_number VARCHAR(50) UNIQUE NOT NULL,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	date_of_birth DATE NOT NULL,
	gender VARCHAR(20) NOT NULL CHECK (
			gender IN ('MALE', 'FEMALE')
	),
	contact_info_id BIGINT REFERENCES contact_info(id),
    academic_info_id BIGINT REFERENCES academic_info(id),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);
CREATE TABLE contact_info (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    address_id BIGINT REFERENCES address(id)
);
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(150),
    city VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100)
);
CREATE TABLE academic_info (
    id BIGSERIAL PRIMARY KEY,
    enrollment_date DATE NOT NULL,
    program VARCHAR(100),
    department VARCHAR(100),
    year_level INTEGER,
    status VARCHAR(32) NOT NULL CHECK (
        status IN (
            'APPLICANT','ADMITTED','ENROLLED','LEAVE_OF_ABSENCE',
            'PROBATION','SUSPENDED','WITHDRAWN','GRADUATED','DISMISSED'
        )
    ),
    gpa NUMERIC(3,2)
);

Select * From student;
SELECT *FROM address;

ALTER TABLE address
	RENAME COLUMN postal_code TO zip_code;









