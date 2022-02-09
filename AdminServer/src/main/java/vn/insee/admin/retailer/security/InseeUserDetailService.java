//package retailer.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import vn.insee.common.Permission;
//import vn.insee.jpa.entity.UserEntity;
//import vn.insee.jpa.repository.UserRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class InseeUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findByPhone(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("username: " + username + " is not found");
//        }
//        return buildUserDetails(user);
//    }
//
//    private InseeUserDetail buildUserDetails(UserEntity user) {
//        Integer roleId = user.getRoleId();
//        Permission per = Permission.findById(roleId);
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        authorityList.add(new SimpleGrantedAuthority(per.getName()));
//        return new InseeUserDetail(user, authorityList);
//    }
//
//    public UserDetails loadUserById(Integer userId) throws Exception {
//        Optional<UserEntity> user = userRepository.findById(userId);
//        return buildUserDetails(user.orElseThrow(() -> new Exception(String.format("userId: %d is not found", user))));
//    }
//
//}
