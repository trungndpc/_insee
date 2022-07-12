package vn.insee.jpa.entity.form;


import vn.insee.jpa.entity.FormEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "greeting_friend_form", schema = "public")
public class GreetingFriendFormEntity extends FormEntity {
    private String jsonImage;
    private Integer bags;
    private Long time;
    public String getJsonImage() {
        return jsonImage;
    }

    public void setJsonImage(String jsonImage) {
        this.jsonImage = jsonImage;
    }

    public Integer getBags() {
        return bags;
    }

    public void setBags(Integer bags) {
        this.bags = bags;
    }


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
