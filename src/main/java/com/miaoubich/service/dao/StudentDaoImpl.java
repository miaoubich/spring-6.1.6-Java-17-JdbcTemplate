package com.miaoubich.service.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.miaoubich.model.Address;
import com.miaoubich.model.Student;


@Repository
public class StudentDaoImpl implements StudentDao {

	private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public Student saveNewStudent(Student student) {
    	saveAddress(student);
    	saveContactInfo(student);
    	saveAcademicInfo(student);
    	return saveStudent(student);
    }
    
	@Override
	public Student saveStudent(Student student) {
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

	   
	    jdbcTemplate.update(sql, sql,
	            student.getStudentNumber(),
	            student.getFirstName(),
	            student.getLastName(),
	            Date.valueOf(student.getDateOfBirth()),
	            student.getGender().name(),
	            student.getContactInfo().getId(),
	            student.getAcademicInfo().getId(),
	            Timestamp.valueOf(student.getCreatedAt()),
	            Timestamp.valueOf(student.getUpdatedAt()));

	    return student;
	}

	private void saveAddress(Student student) {
		Address address = student.getContactInfo().getAddress();
		
		String sql = """
					INSERT INTO address (street, city, state, zip_code, country)
					 VALUES (?, ?, ?, ?, ?) 
					 ON CONFLICT DO NOTHING RETURNING id"
					""";
	    Long addressId = jdbcTemplate.query(sql, ps -> {
	                ps.setString(1, address.getStreet());
	                ps.setString(2, address.getCity());
	                ps.setString(3, address.getZipCode());
	                ps.setString(4, address.getCountry());
	            }, rs -> rs.next()?  rs.getLong("id"): null);
	    
	    if (addressId == null) {
	        // Fallback: fetch existing address ID
	        String selectSql = """
	            SELECT id FROM address
	            WHERE street = ? AND city = ? AND postal_code = ? AND country = ?
	            """;
	        addressId = jdbcTemplate.queryForObject(selectSql, Long.class,
	            address.getStreet(),
	            address.getCity(),
	            address.getZipCode(),
	            address.getCountry()
	        );
	    }

	    address.setId(addressId);
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
	 
	 private void saveContactInfo(Student student) {
			// TODO Auto-generated method stub
			
	}
	 
	 private void saveAcademicInfo(Student student) {
			// TODO Auto-generated method stub
			
	}

}
