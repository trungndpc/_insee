package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.repository.FormRepository;

import java.util.List;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    public List<FormEntity> findByUserId(int userId) {
        return formRepository.findByUserId(userId);
    }
}
