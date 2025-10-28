package com.miaoubich;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.miaoubich.controller.StudentController;
import com.miaoubich.dto.*;
import com.miaoubich.model.Gender;
import com.miaoubich.model.StudentStatus;
import com.miaoubich.service.bo.StudentBo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {StudentController.class})
public class StudentControllerTest {

    private MockMvc mockMvc;
    private StudentBo studentBo;
    private StudentController studentController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        studentBo = Mockito.mock(StudentBo.class);
        studentController = new StudentController(studentBo);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createStudent_ReturnsCreated() throws Exception {
        AddressRequest addressRequest = new AddressRequest("123 Main St", "City", "12345", "Country");
        AcademicInfoRequest academicInfoRequest = new AcademicInfoRequest(
                LocalDate.of(2022, 9, 1), "Computer Science", "Engineering", 3, StudentStatus.ADMITTED, new BigDecimal("3.8")
        );
        ContactInfoRequest contactInfoRequest = new ContactInfoRequest("test@email.com", "1234567890", addressRequest);

        StudentRequest request = new StudentRequest(
                "S2025002",
                "John",
                "Doe",
                LocalDate.of(2000, 10, 28),
                Gender.MALE,
                contactInfoRequest,
                academicInfoRequest,
                LocalDateTime.of(2024, 10, 25, 10, 0, 0),
                LocalDateTime.of(2024, 10, 25, 10, 0, 0)
        );
        String stringRequest = objectMapper.writeValueAsString(request);

        StudentResponse response = new StudentResponse();
        response.setStudentNumber("S2025002");
        response.setFirstName("John");
        response.setLastName("Doe");

        Mockito.when(studentBo.createStudent(any(StudentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentNumber").value("S2025002"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
}
