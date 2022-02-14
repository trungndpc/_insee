package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.message.ApprovedUserMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;
import vn.insee.jpa.repository.UserRepository;
import vn.insee.jpa.specification.UserSpecification;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecification userSpecification;

    public UserEntity createUserFromInseeCustomer(UserEntity customer) throws Exception {
        String phone = customer.getPhone();
        UserEntity exitUserEntity = userRepository.findByPhone(phone);
        if (exitUserEntity != null) {
            throw new Exception("phone is exits!");
        }
        customer.setStatus(UserStatus.WAITING_ACTIVE);
        customer = userRepository.saveAndFlush(customer);
        return customer;
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

    public Page<UserEntity> find(String search, Integer status, Integer location, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<UserEntity> specs =  Specification.where(null);
        if (search != null || !search.isEmpty()) {
            specs.and(userSpecification.likePhone(search).or(userSpecification.likeName(search)));
        }
        if (status != null) {
            specs.and(userSpecification.isStatus(status));
        }
        if (location != null) {
            specs.and(userSpecification.isLocation(location));
        }
        return userRepository.findAll(specs, pageable);
    }

    public UserEntity updateStatus(int uid, int status) throws Exception {
        UserEntity userEntity = userRepository.getOne(uid);
        if (userEntity.getStatus() == StatusUser.APPROVED) {
            throw new Exception("user is approved uid: " + uid);
        }
        if (userEntity.getStatus() != StatusUser.WAIT_APPROVAL) {
            throw new Exception("user do not need approval uid: " + uid);
        }
        userEntity.setStatus(status);
        userRepository.saveAndFlush(userEntity);
        if (status == StatusUser.APPROVED) {
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            ApprovedUserMessage approvedUserMessage = new ApprovedUserMessage(user, userEntity.getName());
            approvedUserMessage.send();
        }
        return userEntity;
    }

    public long count(Integer location, Integer status) {
        Specification<UserEntity> specs =  Specification.where(null);
        if (location != null) {
            specs.and(userSpecification.isLocation(location));
        }
        if (status != null) {
            specs.and(userSpecification.isStatus(status));
        }
        return userRepository.count(specs);
    }

    public List<UserCityMetric> statisticUserByCity() {
        return userRepository.statisticUserByCity();
    }

    public List<UserDataMetric> statisticUserByDate() {
        return userRepository.statisticUserByDate();
    }
}
