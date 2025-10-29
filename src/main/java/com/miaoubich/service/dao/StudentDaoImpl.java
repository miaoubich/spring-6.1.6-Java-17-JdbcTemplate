package com.miaoubich.service.dao;

import com.miaoubich.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class StudentDaoImpl implements StudentDao {

    private final static Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Student saveNewStudent(Student student) {
        saveAddress(student);
        saveContactInfo(student);
        Student savedStudent = saveStudent(student);
        saveAcademicInfo(student);
        return savedStudent;
    }

    private Student saveStudent(Student student) {
        // created_at, updated_at -> these attribute can be set to NOW() in the DB Table definition
        // without including them in the inse
        // rt statement
        // or set them with timestamps before persisting and include them in the sql statement as follow
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        String sql = """
                INSERT INTO student (
                    student_number, first_name, last_name, date_of_birth, gender,
                    contact_info_id, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (student_number)
                DO UPDATE SET
                    first_name = EXCLUDED.first_name,
                    last_name = EXCLUDED.last_name,
                    date_of_birth = EXCLUDED.date_of_birth,
                    gender = EXCLUDED.gender,
                    contact_info_id = EXCLUDED.contact_info_id,
                    updated_at = EXCLUDED.updated_at
                RETURNING student_id, created_at, updated_at
                """;

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    student.setId(rs.getLong("student_id"));
                    student.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    student.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    return student;
                },
                student.getStudentNumber(),
                student.getFirstName(),
                student.getLastName(),
                Date.valueOf(student.getDateOfBirth()),
                student.getGender().name(),
                student.getContactInfo().getId(),
                Timestamp.valueOf(student.getCreatedAt()),
                Timestamp.valueOf(student.getUpdatedAt())
        );
    }

    private void saveAddress1(Student student) {
        Address address = student.getContactInfo().getAddress();

        String sql = """
                INSERT INTO address (street, city, zip_code, country)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (street, city, zip_code, country)
                DO UPDATE SET street = EXCLUDED.street
                RETURNING id
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getZipCode());
            ps.setString(4, address.getCountry());
            return ps;
        }, keyHolder);

        // Extract the returned ID (PostgreSQL returns it in the key holder’s map)
        Number key = (keyHolder.getKey() != null)
                ? keyHolder.getKey()
                : (Number) keyHolder.getKeys().get("id");

        if (key == null) {
            // fallback select in the rare case the update didn't return
            String selectSql = """
                    SELECT id FROM address
                    	WHERE street = ? 
                    	  AND city = ? 
                    	  AND zip_code = ? 
                    	  AND country = ?
                    """;
            key = jdbcTemplate.queryForObject(
                    selectSql,
                    Long.class,
                    address.getStreet(),
                    address.getCity(),
                    address.getZipCode(),
                    address.getCountry()
            );
        }
        address.setId(key.longValue());
    }
    private void saveAddress(Student student) {
        Address address = student.getContactInfo().getAddress();

        String sql = """
                       INSERT INTO address (street, city, zip_code, country) 
                            VALUES (?, ?, ?, ?) 
                            ON CONFLICT (street, city, zip_code, country) 
                            DO UPDATE SET 
                            street = EXCLUDED.street, 
                            city = EXCLUDED.city,
                            zip_code = EXCLUDED.zip_code,
                            country = EXCLUDED.country
                            RETURNING id
                        """;
        Long addressId = jdbcTemplate.query(sql,
                ps -> {
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getZipCode());
            ps.setString(4, address.getCountry());
        }, rs -> rs.next() ? rs.getLong("id") : null);

        if (addressId == null) {
            // Fallback: fetch existing address ID
            String selectSql =
                    "SELECT id FROM address " +
                            "WHERE street = ? " +
                            "AND city = ? " +
                            "AND zip_code = ? " +
                            "AND country = ? ";
            addressId = jdbcTemplate.queryForObject(
                    selectSql,
                    Long.class,
                    address.getStreet(),
                    address.getCity(),
                    address.getZipCode(),
                    address.getCountry()
            );
        }
        address.setId(addressId);
    }

    private void saveContactInfo(Student student) {
        ContactInfo contactInfo = student.getContactInfo();
        String sql = """
                INSERT INTO contact_info (email, phone_number, address_id)
                VALUES (?, ?, ?)
                ON CONFLICT (email)
                DO UPDATE SET
                    email = EXCLUDED.email,
                    phone_number = EXCLUDED.phone_number,
                    address_id = EXCLUDED.address_id
                RETURNING id
                """;
        String sql2 = """
                INSERT INTO contact_info (email, phone_number, address_id)
                VALUES (?, ?, ?)
                ON CONFLICT (email)
                DO UPDATE SET
                	phone_number = EXCLUDED.phone_number,
                	address_id = EXCLUDED.address_id
                WHERE contact_info.phone_number IS DISTINCT FROM EXCLUDED.phone_number,
                OR contact_info.address_id != EXCLUDED.address_id
                RETURNING id
                """;
        Long contactInfoId = jdbcTemplate.query(sql, ps -> {
            ps.setString(1, contactInfo.getEmail());
            ps.setString(2, contactInfo.getPhoneNumber());
            ps.setLong(3, contactInfo.getAddress().getId());
        }, rs -> rs.next() ? rs.getLong("id") : null);
        contactInfo.setId(contactInfoId);
    }

    private void saveAcademicInfo(Student student) {
        AcademicInfo academicInfo = student.getAcademicInfo();

        String sql = """
                INSERT INTO academic_info (enrollment_date, program, department, year_level, status, gpa, student_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (student_id)
                DO UPDATE SET
                    enrollment_date = EXCLUDED.enrollment_date,
                    program = EXCLUDED.program,
                    department = EXCLUDED.department,
                    year_level = EXCLUDED.year_level,
                	status = EXCLUDED.status,
                    gpa = EXCLUDED.gpa
                RETURNING id
                """;
        Long academicInfoId = jdbcTemplate.queryForObject(
                sql,
                new Object[]{
                        Date.valueOf(academicInfo.getEnrollmentDate()),
                        academicInfo.getProgram(),
                        academicInfo.getDepartment(),
                        academicInfo.getYearLevel(),
                        academicInfo.getStudentStatus().name(),
                        academicInfo.getGpa(),
                        student.getId()
                },
                (rs, rowNum) -> rs.getLong("id")
        );
    }

    @Override
    public Student getStudentByStudentNumber(String studentNumber) {
        logger.info("Fetching student with student number: " + studentNumber);

        String sql = """
                SELECT s.student_id, 
                       s.student_number, s.first_name, s.last_name, s.date_of_birth,
                       s.created_at, s.updated_at,
                       ad.street, ad.zip_code, ad.city, ad.country,
                       c.email, c.phone_number,
                       ac.program, ac.department, ac.year_level, ac.enrollment_date, ac.status, ac.gpa
                FROM student s
                JOIN contact_info c ON s.contact_info_id = c.id
                JOIN address ad ON c.address_id = ad.id
                JOIN academic_info ac ON s.student_id = ac.student_id
                WHERE s.student_number = ?
                """;
        // Implement the logic to fetch and return the student by student number
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rows) -> {
                // Address
                Address address = new Address();
                address.setStreet(rs.getString("street"));
                address.setZipCode(rs.getString("zip_code"));
                address.setCity(rs.getString("city"));
                address.setCountry(rs.getString("country"));
                logger.info("Address fetched: {}", address);

                // ContactInfo
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setEmail(rs.getString("email"));
                contactInfo.setPhoneNumber(rs.getString("phone_number"));

                // Set Address in ContactInfo
                contactInfo.setAddress(address);
                logger.info("ContactInfo fetched: {}", contactInfo);

                // AcademicInfo
                AcademicInfo academicInfo = new AcademicInfo();
                academicInfo.setProgram(rs.getString("program"));
                academicInfo.setDepartment(rs.getString("department"));
                academicInfo.setYearLevel(rs.getInt("year_level"));
                academicInfo.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate());
                academicInfo.setStudentStatus(Enum.valueOf(StudentStatus.class, rs.getString("status")));
                academicInfo.setGpa(rs.getBigDecimal("gpa"));
                logger.info("AcademicInfo fetched: {}", academicInfo);

                // Student
                Student student = new Student();
                student.setId(rs.getLong("student_id"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                student.setContactInfo(contactInfo);
                student.setAcademicInfo(academicInfo);
                student.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                student.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                logger.info("Student fetched: {}", student);

                return student;
            }, studentNumber);
        } catch (EmptyResultDataAccessException e) {
            logger.info("No student found with student number: " + studentNumber);
            return null;
        }
    }

    @Override
    public Student updateStudentByStudentStatus(String studentNumber, StudentStatus newStatus) {
        String sql = """
                UPDATE academic_info ai
                SET status = ?
                FROM student s
                WHERE s.student_id = ai.student_id
                  AND s.student_number = ?
                RETURNING ai.id
                """;

        Long academicInfoId = jdbcTemplate.queryForObject(
                sql,
                new Object[]{newStatus.name(), studentNumber},
                Long.class
        );
        if (academicInfoId != null) {
            logger.info("Updated status for student number {}: new status {}", studentNumber, newStatus);
            return getStudentByStudentNumber(studentNumber);
        } else {
            logger.warn("No student found with student number: " + studentNumber);
            return null;
        }
    }

    @Override
    public List<Student> saveStudents(List<Student> students) {
        String sql = """
                INSERT INTO student (
                    student_number, first_name, last_name, date_of_birth, gender,
                    contact_info_id, academic_info_id, created_at, updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (student_number)
                DO UPDATE SET
                    first_name = EXCLUDED.first_name,
                    last_name = EXCLUDED.last_name,
                    date_of_birth = EXCLUDED.date_of_birth,
                    gender = EXCLUDED.gender,
                    contact_info_id = EXCLUDED.contact_info_id,
                    academic_info_id = EXCLUDED.academic_info_id,
                    updated_at = NOW()
                """;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Student student = students.get(i);
                ps.setString(1, student.getStudentNumber());
                ps.setString(2, student.getFirstName());
                ps.setString(3, student.getLastName());
                ps.setDate(4, Date.valueOf(student.getDateOfBirth()));
                ps.setString(5, student.getGender().name());
                ps.setLong(6, student.getContactInfo().getId());
                ps.setLong(7, student.getAcademicInfo().getId());
                ps.setTimestamp(8, Timestamp.valueOf(student.getCreatedAt()));
                ps.setTimestamp(9, Timestamp.valueOf(student.getUpdatedAt()));
            }

            @Override
            public int getBatchSize() {
                return students.size();
            }
        });
        return students;
    }
}
