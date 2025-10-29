package com.miaoubich;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.miaoubich.controller.StudentController;
import com.miaoubich.dto.*;
import com.miaoubich.model.Gender;
import com.miaoubich.model.StudentStatus;
import com.miaoubich.service.bo.StudentBo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
    @DisplayName("Create Student - Success")
    void createStudent_ReturnsCreated() throws Exception {
        AddressRequest addressRequest = new AddressRequest("123 Main St", "City", "12345", "Country");
        AcademicInfoRequest academicInfoRequest = new AcademicInfoRequest(
                LocalDate.of(2022, 9, 1), "Computer Science", "Engineering", 3, StudentStatus.ADMITTED, new BigDecimal("3.8")
        );
        ContactInfoRequest contactInfoRequest = new ContactInfoRequest("test@email.com", "1234567890", addressRequest);

        StudentRequest request = new StudentRequest(
                "S2025002",
                "ALi",
                "Bouzar",
                LocalDate.of(2000, 10, 28),
                Gender.MALE,
                contactInfoRequest,
                academicInfoRequest,
                LocalDateTime.of(2024, 10, 25, 10, 0, 0),
                LocalDateTime.of(2025, 9, 11, 12, 0, 0)
        );
        String stringRequest = objectMapper.writeValueAsString(request);

        StudentResponse response = new StudentResponse();
        response.setStudentNumber("S2025002");
        response.setFirstName("Ali");
        response.setLastName("Bouzar");

        Mockito.when(studentBo.upsertStudent(any(StudentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentNumber").value("S2025002"))
                .andExpect(jsonPath("$.firstName").value("Ali"))
                .andExpect(jsonPath("$.lastName").value("Bouzar"));
    }

    @Test
    @DisplayName("Create Student - Bad Request")
    void createStudent_ReturnsBadRequest() throws Exception {
        String invalidJson = "{ \"studentNumber\": \"S2025002\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"dateOfBirth\": \"invalid-date\" }";

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("getStudentByStudentNumber - Success")
    void getStudentByStudentNumber_ReturnsOk() throws Exception {
        AddressResponse addressResponse = new AddressResponse("S2025002", "City", "12345", "Country");
        ContactInfoResponse contactInfoResponse = new ContactInfoResponse("test@email.com", "1234567890", addressResponse);
        AcademicInfoResponse academicInfoResponse = new AcademicInfoResponse(
                LocalDate.of(2022, 9, 1), "Computer Science", "Engineering", 3, StudentStatus.ADMITTED, new BigDecimal("3.8")
        );
        StudentResponse response = new StudentResponse(
                1L, // id
                "S2025002", // studentNumber
                "ALi", // firstName
                "Bouzar", // lastName
                LocalDate.of(2000, 10, 28), // dateOfBirth
                Gender.MALE,
                contactInfoResponse, // contactInfoResponse
                academicInfoResponse, // academicInfoResponse
                LocalDateTime.of(2024, 10, 25, 10, 0, 0), // createdAt
                LocalDateTime.of(2024, 10, 25, 10, 0, 0) // updatedAt
        );
        Mockito.when(studentBo.findStudentByStudentNumber("S2025002")).thenReturn(java.util.Optional.of(response));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/{studentNumber}", "S2025002")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.studentNumber").value("S2025002"))
                .andExpect(jsonPath("$.firstName").value("ALi"))
                .andExpect(jsonPath("$.lastName").value("Bouzar"));
    }

    @Test
    @DisplayName("getStudentByStudentNumber - Not Found")
    void getStudentByStudentNumber_ReturnsNotFound() throws Exception {
        Mockito.when(studentBo.findStudentByStudentNumber("S9999999")).thenReturn(java.util.Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/{studentNumber}", "S9999999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update Student Status - Success") //updateStudentStatus(@PathVariable String studentNumber, @RequestParam String status)
    void updateStudentStatus_ReturnsOk() throws Exception {
        AddressResponse addressResponse = new AddressResponse("S2025002", "City", "12345", "Country");
        ContactInfoResponse contactInfoResponse = new ContactInfoResponse("test@email.com", "1234567890", addressResponse);
        AcademicInfoResponse academicInfoResponse = new AcademicInfoResponse(
                LocalDate.of(2022, 9, 1), "Computer Science", "Engineering", 3, StudentStatus.ADMITTED, new BigDecimal("3.8")
        );
        StudentResponse response = new StudentResponse(
                1L, // id
                "S2025002", // studentNumber
                "ALi", // firstName
                "Bouzar", // lastName
                LocalDate.of(2000, 10, 28), // dateOfBirth
                Gender.MALE,
                contactInfoResponse, // contactInfoResponse
                academicInfoResponse, // academicInfoResponse
                LocalDateTime.of(2024, 10, 25, 10, 0, 0), // createdAt
                LocalDateTime.of(2024, 10, 25, 10, 0, 0) // updatedAt
        );
        Mockito.when(studentBo.updateStudentStatus("S2025002", StudentStatus.ADMITTED)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/students/{studentNumber}?status={status}", "S2025002", "ENROLLED")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentNumber").value("S2025002"))
                .andExpect(jsonPath("$.firstName").value("ALi"))
                .andExpect(jsonPath("$.lastName").value("Bouzar"))
                .andExpect(jsonPath("$.academicInfoResponse.studentStatus").value("ADMITTED"));
    }
}