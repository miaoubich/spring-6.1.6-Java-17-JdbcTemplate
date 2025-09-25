package com.miaoubich.service.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.miaoubich.model.AcademicInfo;
import com.miaoubich.model.Address;
import com.miaoubich.model.ContactInfo;
import com.miaoubich.model.Student;

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
		saveAcademicInfo(student);
		return saveStudent(student);
	}

	private Student saveStudent(Student student) {
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

		long affectedRows = jdbcTemplate.update(sql, student.getStudentNumber(), student.getFirstName(), student.getLastName(),
				Date.valueOf(student.getDateOfBirth()), student.getGender().name(), student.getContactInfo().getId(),
				student.getAcademicInfo().getId(), Timestamp.valueOf(student.getCreatedAt()),
				Timestamp.valueOf(student.getUpdatedAt()));

		logger.info("Affected rows: " + affectedRows);
		return student;
	}

	private void saveAddress(Student student) {
		Address address = student.getContactInfo().getAddress();

		String sql = """
				INSERT INTO address (street, city, zip_code, country)
				 VALUES (?, ?, ?, ?)
				 ON CONFLICT DO NOTHING RETURNING id
				""";
		Long addressId = jdbcTemplate.query(sql, ps -> {
			ps.setString(1, address.getStreet());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getZipCode());
			ps.setString(4, address.getCountry());
		}, rs -> rs.next() ? rs.getLong("id") : null);

		if (addressId == null) {
			// Fallback: fetch existing address ID
			String selectSql = """
					SELECT id FROM address
					WHERE street = ? AND city = ? AND postal_code = ? AND country = ?
					""";
			addressId = jdbcTemplate.queryForObject(selectSql, Long.class, address.getStreet(), address.getCity(),
					address.getZipCode(), address.getCountry());
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
					status = EXCLUDED.status,
				    gpa = EXCLUDED.gpa
				WHERE academic_info.status IS DISTINCT FROM EXCLUDED.status
				OR academic_info.gpa != EXCLUDED.gpa
				RETURNING id
				""";
		Long academicInfoId = jdbcTemplate.query(sql, ps -> {
			ps.setDate(1, Date.valueOf(academicInfo.getEnrollmentDate()));
			ps.setString(2, academicInfo.getProgram());
			ps.setString(3, academicInfo.getDepartment());
			ps.setInt(4, academicInfo.getYearLevel());
			ps.setString(5, academicInfo.getStudentStatus().name());
			ps.setBigDecimal(6, academicInfo.getGpa());
			ps.setObject(7, student.getId());
		}, rs -> rs.next() ? rs.getLong("id") : null);
		academicInfo.setId(academicInfoId);
	}

	@Override
	public void saveStudents(List<Student> students) {
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
	}

}
