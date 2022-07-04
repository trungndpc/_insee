package vn.insee.jpa.entity.promotion;

import vn.insee.jpa.entity.PromotionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "greeting_friend_promotion", schema = "public")
public class GreetingFriendPromotionEntity  extends PromotionEntity {
}
