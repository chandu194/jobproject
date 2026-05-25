package com.embarkx.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/companies")
@RestController
public class CompanyController {
    CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> findAll(){
       return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company company =companyService.getCompanyById(id);
        if(company!= null)
            return new ResponseEntity<>(company, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Long id, @RequestBody Company company){
        boolean isUpdated=companyService.updateCOmpany(company,id);
        if(isUpdated)
            return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
        return new ResponseEntity<>("Failed to update", HttpStatus.NOT_MODIFIED);

    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company){
        companyService.createComapny(company);
        return new ResponseEntity<>("Company created", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
        boolean isDeleted=companyService.deleteById(id);
        if(isDeleted)
        return new ResponseEntity<>("Comapany is deleted", HttpStatus.OK);
        return new ResponseEntity<>("Failed to delete", HttpStatus.NOT_FOUND);
    }
}
