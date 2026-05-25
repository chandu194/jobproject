package com.embarkx.jobms.job.mapper;

import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.dto.JobDto;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDto mapJobDto(Job job, Company company, List<Review> reviews){
        JobDto jobWithCompanyDto = new JobDto();
        jobWithCompanyDto.setId(job.getId());
        jobWithCompanyDto.setDescription(job.getDescription());
        jobWithCompanyDto.setLocation(job.getLocation());
        jobWithCompanyDto.setTitle(job.getTitle());
        jobWithCompanyDto.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDto.setMinSalary(job.getMinSalary());
        jobWithCompanyDto.setCompany(company);
        jobWithCompanyDto.setReviews(reviews);
        return jobWithCompanyDto;

    }
}
