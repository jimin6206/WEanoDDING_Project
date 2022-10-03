package com.ssafy.api.service;

import com.ssafy.api.request.CompanyRegisterPostReq;
import com.ssafy.api.request.CompanyUpdatePostReq;
import com.ssafy.db.entity.Company;
import com.ssafy.db.entity.Hall;
import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.Wedding;
import com.ssafy.db.repository.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    WeddingRepository weddingRepository;

    @Autowired
    CompanyRepositorySupport CompanyRepositorySupport;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Company createCompany(CompanyRegisterPostReq companyRegisterInfo) {
        Company company = new Company();
        company.setCompanyId(companyRegisterInfo.getCompanyId());
        company.setCompanyName(companyRegisterInfo.getCompanyName());
        // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
        company.setCompanyPassword(passwordEncoder.encode(companyRegisterInfo.getCompanyPassword()));
        company.setCompanyTelnum(companyRegisterInfo.getCompanyTelnum());
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(String companyId) {

        //Hall 삭제
        List<Hall> halls = hallRepository.findByCompanyId(companyId);
        for (Hall hall: halls) {
            hallRepository.delete(hall);
        }

        //Question 삭제
        List<Question> questions = questionRepository.findByCompanyId(companyId);
        for (Question question: questions) {
            questionRepository.delete(question);
        }

        //wedding 삭제
        List<Wedding> weddings = weddingRepository.findByCompanyId(companyId);
        for (Wedding wedding:weddings) {
            weddingRepository.delete(wedding);
        }

        //company 삭제
        companyRepository.delete(selectCompany(companyId));
        return;
    }

    @Override
    public Company updateCompany(String companyId, CompanyUpdatePostReq companyUpdateInfo) {
        Company company = selectCompany(companyId);
        company.setCompanyName(companyUpdateInfo.getCompanyName());
        // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
        company.setCompanyPassword(passwordEncoder.encode(companyUpdateInfo.getCompanyPassword()));
        company.setCompanyTelnum(companyUpdateInfo.getCompanyTelnum());
        return companyRepository.save(company);

    }

    @Override
    public Company selectCompany(String companyId) {
        Optional<Company> optCompany = CompanyRepositorySupport.findCompanyByCompanyId(companyId);
        return optCompany.isPresent() ? optCompany.get(): null;
    }
}
