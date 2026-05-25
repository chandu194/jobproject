package com.embarkx.companyms.company.impl;

import com.embarkx.companyms.company.Company;
import com.embarkx.companyms.company.CompanyRepository;
import com.embarkx.companyms.company.CompanyService;
import com.embarkx.companyms.company.clients.ReviewClient;
import com.embarkx.companyms.company.dto.ReviewMessage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    CompanyRepository companyRepository;
    ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> findAll() {
      return companyRepository.findAll();
    }

    @Override
    public boolean updateCOmpany(Company company, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()){
            Company company1 = companyOptional.get();
            company1.setDescription(company.getDescription());
            company1.setName(company.getName());

            companyRepository.save(company1);
            return  true;
        }
return false;
    }

    @Override
    public void createComapny(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            companyRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public Company getCompanyById(Long id) {
       return companyRepository.findById(id).orElse(null);

    }

    @Override
     public void companyUpdateRating(ReviewMessage reviewMessage) {
      System.out.println(reviewMessage.getDescription());
      Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
      double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
      company.setRating(averageRating);
      companyRepository.save(company);
    }


}
