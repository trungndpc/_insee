package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.retailer.controller.dto.CustomerDTO;
import vn.insee.retailer.controller.form.RegisterForm;
import vn.insee.retailer.mapper.Mapper;

@Component
public class CustomerConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public CustomerEntity convert2Entity(RegisterForm registerForm) {
        return mapper.map(registerForm, CustomerEntity.class);
    }

    public CustomerDTO convert2DTO(CustomerEntity customerEntity) {
        return mapper.map(customerEntity, CustomerDTO.class);
    }

}
