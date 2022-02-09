//package retailer.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import retailer.common.UserStatus;
//import vn.insee.jpa.entity.UserEntity;
//
//import java.util.Collection;
//
//public class InseeUserDetail implements UserDetails {
//    private final UserEntity user;
//    private final Collection<? extends GrantedAuthority> grantedAuthorities;
//
//    public UserEntity getUser() {
//        return user;
//    }
//
//    public InseeUserDetail(UserEntity user, Collection<? extends GrantedAuthority> grantedAuthorities) {
//        this.user = user;
//        this.grantedAuthorities = grantedAuthorities;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.grantedAuthorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getPhone();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.getStatus() != UserStatus.DISABLED;
//    }
//}
