package vn.insee.jpa.entity;



import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema="promotion")
@TypeDef(name = "list-array",typeClass = ListArrayType.class)
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phone;
    private String password;
    private Integer status;
    private String zaloId;
    private String followerZaloId;
    private String avatar;
    private Integer customerId;
    private Integer roleId;
    private String name;
    private boolean isEnable;
    private String note;
    private Integer birthday;
    private Integer referralUser;
    private Integer referralCode;

    @Type(type = "list-array")
    @Column(name = "lst_session",columnDefinition = "character varying[]")
    private List<String> lstSession;

    public boolean isValid() {
        //TODO
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getZaloId() {
        return zaloId;
    }

    public void setZaloId(String zaloId) {
        this.zaloId = zaloId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLstSession() {
        return lstSession;
    }

    public void setLstSession(List<String> lstSession) {
        this.lstSession = lstSession;
    }

    public String getFollowerZaloId() {
        return followerZaloId;
    }

    public void setFollowerZaloId(String followerZaloId) {
        this.followerZaloId = followerZaloId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public Integer getReferralUser() {
        return referralUser;
    }

    public void setReferralUser(Integer referralUser) {
        this.referralUser = referralUser;
    }

    public Integer getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(Integer referralCode) {
        this.referralCode = referralCode;
    }
}
