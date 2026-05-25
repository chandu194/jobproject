package com.embarkx.jobms.job;


import com.embarkx.jobms.job.dto.JobDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/jobs")
@RestController

public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> findAll(){
      return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody  Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job created successfully",HttpStatus.CREATED);
    }
@GetMapping("/{id}")
//@RequestMapping(value = "/jobs/{id}", method = RequestMethod.GET)
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id){

    JobDto jobDto = jobService.getJobById(id);
       if(jobDto!= null)
       return new ResponseEntity<>(jobDto, HttpStatus.OK);
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean isDeleted = jobService.deleteById(id);
        if(isDeleted) {
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Not Deleted",HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job updateBody){
        boolean isUpdated = jobService.updateById(id,updateBody);
        if(isUpdated)
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        return new ResponseEntity<>("Failed to update", HttpStatus.NOT_MODIFIED);

    }
}
