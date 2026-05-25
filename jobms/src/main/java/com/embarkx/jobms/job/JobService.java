package com.embarkx.jobms.job;


import com.embarkx.jobms.job.dto.JobDto;

import java.util.List;


public interface JobService {
    List<JobDto> findAll();
    void createJob(Job job);

    JobDto getJobById(Long id);

    boolean deleteById(Long id);

    boolean updateById(Long id, Job job);
}
