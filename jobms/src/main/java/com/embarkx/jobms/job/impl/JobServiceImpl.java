package com.embarkx.jobms.job.impl;

import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import com.embarkx.jobms.job.clients.CompanyClient;
import com.embarkx.jobms.job.clients.ReviewClient;
import com.embarkx.jobms.job.dto.JobDto;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;
import com.embarkx.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    JobRepository jobRepository;
    //private Long nextId = 1L;

    @Autowired
    RestTemplate restTemplate;

    CompanyClient companyClient;
    ReviewClient reviewClient;

    int attempt=0;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    //@CircuitBreaker(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    //@Retry(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name="companyBreaker")
    public List<JobDto> findAll() {
        System.out.println("Attempt :"+ ++attempt);
        List<Job> jobList = jobRepository.findAll();
        return jobList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("dummy");
        return list;
    }
    private JobDto convertToDto(Job job){


       // RestTemplate restTemplate = new RestTemplate();
        //JobWithCompanyDto jobWithCompanyDto = new JobWithCompanyDto();
        // since e removed the Job class from dto class and added the fields from job class this giving error
        //so need to use mapper class

        //jobWithCompanyDto.setJob(job);


        //this changes done for structure change the mappers introduced in json structure change it ll remove the job tag
        //and nested structure as well




//        Company  company=restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });

        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviewList(job.getCompanyId());
        JobDto jobDto = JobMapper.mapJobDto(job,company,reviews);
        return jobDto;
    }

    @Override
    public void createJob(Job job) {

     jobRepository.save(job);
    }

    @Override
    public JobDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);

    }

    @Override
    public boolean deleteById(Long id) {
        try {

            jobRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean updateById(Long id, Job updateBody) {
        Optional<Job> jobOptional = jobRepository.findById(id);

    if(jobOptional.isPresent()){
        Job job = jobOptional.get();
        job.setDescription(updateBody.getDescription());
        job.setTitle(updateBody.getTitle());
        job.setLocation(updateBody.getLocation());
        job.setMinSalary(updateBody.getMinSalary());
        job.setMaxSalary(updateBody.getMaxSalary());
        jobRepository.save(job);
        return true;
    }

        return false;
    }
}
