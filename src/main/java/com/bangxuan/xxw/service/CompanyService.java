package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.CompanyMapper;
import java.util.List;

import com.bangxuan.xxw.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    public List<String> getLogos() {

        return companyMapper.selectLogos();
    }

    public Integer getCount() {
        return companyMapper.selectCount();
    }

    public Company getIncomplete() {
        return companyMapper.getIncomplete();
    }

    public List<Company> getIncompleteALL() {
        return companyMapper.getIncompleteAll();
    }

    @Transactional
    public int update(Company company) {
        return companyMapper.updateByPrimaryKeySelective(company);
    }

    public Company getById(String id) {
        return companyMapper.getById(id);
    }
}
