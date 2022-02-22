package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.UserRepository;
import vn.insee.jpa.specification.UserSpecification;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecification userSpecification;

    public UserEntity register(UserEntity userEntity) throws Exception {
        if (userEntity.getStatus() != StatusUser.WAIT_COMPLETE_PROFILE) {
            throw new Exception("status is not valid to update profile | status: " + userEntity.getStatus() + ", id: " + userEntity.getId());
        }
        String phone = userEntity.getPhone();
        UserEntity exitUserEntity = userRepository.findByPhone(phone);
        if (exitUserEntity != null) {
            if (exitUserEntity.getStatus() == StatusUser.APPROVED) {
                throw new Exception("status is not valid to update profile | phone: " + userEntity.getPhone() + ", id: " + userEntity.getId());
            }
            if (exitUserEntity.getStatus() == StatusUser.WAITING_ACTIVE) {
                userEntity.setPairingId(exitUserEntity.getId());
                userEntity.setAddress(exitUserEntity.getAddress());
                userEntity.setDistrictId(exitUserEntity.getDistrictId());
                userEntity.setCityId(exitUserEntity.getCityId());
            }
        }
        userEntity.setStatus(StatusUser.WAIT_APPROVAL);
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }

    public UserEntity findByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

    public UserEntity findById(int id) {
        return userRepository.getOne(id);
    }

    public UserEntity findByZaloId(long zaloId) {
        return userRepository.findByZaloId(String.valueOf(zaloId));
    }

    public UserEntity saveOrUpdate(UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    public List<UserEntity> findBy(List<Integer> cityIds, List<Integer> districtIds, Integer status) {
        Specification<UserEntity> specs =  Specification.where(null);
        if (status != null) {
            specs = specs.and(userSpecification.isStatus(status));
        }
        if (cityIds != null) {
            specs = specs.and(userSpecification.inCity(cityIds));
        }

        if (districtIds != null) {
            specs = specs.and(userSpecification.inDistrict(districtIds));
        }
        return userRepository.findAll(specs);
    }
}
