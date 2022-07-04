package vn.insee.admin.retailer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.message.ApprovedUserMessage;
import vn.insee.admin.retailer.message.RejectedUserMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.common.Permission;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;
import vn.insee.jpa.repository.UserRepository;
import vn.insee.jpa.specification.UserSpecification;

import java.util.List;

@Service
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecification userSpecification;

    public UserEntity update(UserEntity userEntity) {
        userEntity = userRepository.saveAndFlush(userEntity);
        return userEntity;
    }

    public UserEntity createUserFromInseeCustomer(UserEntity customer) throws Exception {
        String phone = customer.getPhone();
        UserEntity exitUserEntity = userRepository.findByPhone(phone);
        if (exitUserEntity != null) {
            throw new Exception("phone is exits!");
        }
        customer.setRoleId(Permission.RETAILER.getId());
        customer.setStatus(UserStatus.WAITING_ACTIVE);
        customer = userRepository.saveAndFlush(customer);
        return customer;
    }

    public UserEntity findByPhone(String phone) {
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
        Specification<UserEntity> specs = userSpecification.isNotStatus(UserStatus.DISABLED);
        if (search != null && !search.isEmpty()) {
            specs = specs.and(userSpecification.likePhone(search).or(userSpecification.likeName(search)));
        }
        if (status != null) {
            specs = specs.and(userSpecification.isStatus(status));
        }
        if (location != null) {
            specs = specs.and(userSpecification.isCity(location));
        }
        return userRepository.findAll(specs, pageable);
    }


    public List<UserEntity> list(String search, Integer status, Integer location) {
        Specification<UserEntity> specs = Specification.where(null);
        if (search != null && !search.isEmpty()) {
            specs = specs.and(userSpecification.likePhone(search).or(userSpecification.likeName(search)));
        }
        if (status != null) {
            specs = specs.and(userSpecification.isStatus(status));
        }
        if (location != null) {
            specs = specs.and(userSpecification.isCity(location));
        }
        return userRepository.findAll(specs);
    }

    public UserEntity updateStatus(int uid, int status, String note) throws Exception {
        UserEntity userEntity = userRepository.getOne(uid);
        if (userEntity.getStatus() == StatusUser.APPROVED && status != StatusUser.DISABLED) {
            throw new Exception("user is approved uid: " + uid);
        }
        if (status != StatusUser.DISABLED && userEntity.getStatus() != StatusUser.WAIT_APPROVAL) {
            throw new Exception("user do not need approval uid: " + uid);
        }

        userEntity.setStatus(status);
        User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
        if (status == StatusUser.APPROVED) {
            if (userEntity.getPairingId() != null) {
                userRepository.deleteById(userEntity.getPairingId());
                userEntity.setPairingId(null);
            }
            ApprovedUserMessage approvedUserMessage = new ApprovedUserMessage(user, userEntity.getName());
            approvedUserMessage.send();
        }

        if (status == StatusUser.REJECTED) {
            userEntity.setNote(note);
            RejectedUserMessage rejectedUserMessage = new RejectedUserMessage(user, note);
            rejectedUserMessage.send();
        }
        userEntity = userRepository.saveAndFlush(userEntity);
        return userEntity;
    }

    public long count(Integer location, List<Integer> statuses) {
        Specification<UserEntity> specs = Specification.where(null);
        if (location != null) {
            specs = specs.and(userSpecification.isCity(location));
        }
        if (statuses != null) {
            specs = specs.and(userSpecification.inStatus(statuses));
        }
        return userRepository.count(specs);
    }

    public List<UserCityMetric> statisticUserByCity() {
        return userRepository.statisticUserByCity();
    }

    public List<UserDataMetric> statisticUserByDate(List<Integer> statuses) {
        return userRepository.statisticUserByDate(statuses);
    }

    public List<UserEntity> findBy(List<Integer> cityIds, List<Integer> districtIds, Integer status) {
        Specification<UserEntity> specs = Specification.where(null);
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

    public long countBy(List<Integer> cityIds, List<Integer> districtIds, Integer status) {
        Specification<UserEntity> specs = Specification.where(null);
        if (status != null) {
            specs = specs.and(userSpecification.isStatus(status));
        }
        if (cityIds != null) {
            specs = specs.and(userSpecification.inCity(cityIds));
        }

        if (districtIds != null) {
            specs = specs.and(userSpecification.inDistrict(districtIds));
        }
        return userRepository.count(specs);
    }
}
