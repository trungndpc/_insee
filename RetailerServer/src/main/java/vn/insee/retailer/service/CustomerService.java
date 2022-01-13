package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.CustomerStatus;
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.CustomerRepository;
import vn.insee.jpa.repository.UserRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public CustomerEntity register(UserEntity userEntity, CustomerEntity customerEntity) throws Exception {
        String phone = customerEntity.getPhone();
        if (customerRepository.findByPhone(phone) != null) {
            throw new Exception("phone is valid: " + customerEntity.getPhone());
        }
        customerEntity.setAvatar(userEntity.getAvatar());
        customerEntity.setBirthday(userEntity.getBirthday());
        customerEntity.setUserId(userEntity.getId());
        customerEntity.setStatus(CustomerStatus.REVIEWING.getStatus());
        customerEntity = customerRepository.saveAndFlush(customerEntity);
        userEntity.setCustomerId(customerEntity.getId());
        userRepository.saveAndFlush(userEntity);
        return customerEntity;
    }

    public CustomerEntity getById(int id) {
        return customerRepository.getOne(id);
    }
}
