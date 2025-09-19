package com.miaoubich.model;

public enum StudentStatus {
	APPLICANT, // Submitted application, not yet admitted
	ADMITTED, // Offered a place, not enrolled yet
	ENROLLED, // Actively studying (current term)
	LEAVE_OF_ABSENCE, // Temporarily inactive, intends to return
	PROBATION, // Enrolled with academic standing issues
	SUSPENDED, // Disciplinary/administrative suspension
	WITHDRAWN, // Left program without completing
	GRADUATED, // Completed all requirements
	DISMISSED // Removed from program (non‑returning)
}
