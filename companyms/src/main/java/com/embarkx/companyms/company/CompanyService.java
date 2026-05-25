package com.embarkx.companyms.company;

import com.embarkx.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();
    boolean updateCOmpany(Company company, Long id);

    void createComapny(Company company);

    boolean deleteById(Long id);

    Company getCompanyById(Long id);

    public void companyUpdateRating(ReviewMessage  reviewMessage);

}
