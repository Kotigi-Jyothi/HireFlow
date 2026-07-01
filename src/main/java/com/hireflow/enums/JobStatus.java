package com.hireflow.enums;

//lifecycle of a job posting.
public enum JobStatus {
	 	DRAFT, //HR created the job but it's not visible to candidates yet.
	    PUBLISHED, //Candidates can apply.
	    CLOSED     //No more applications accepted.
}
