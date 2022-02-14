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
import vn.insee.jpa.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public Page<UserEntity> find(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<UserEntity> specs =  Specification.where(null);
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
        if (status == StatusUser.APPROVED) {
            if (userEntity.getPairingId() != null) {
                userRepository.deleteById(userEntity.getPairingId());
                userEntity.setPairingId(null);
            }
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            ApprovedUserMessage approvedUserMessage = new ApprovedUserMessage(user, userEntity.getName());
            approvedUserMessage.send();
        }
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }
}
