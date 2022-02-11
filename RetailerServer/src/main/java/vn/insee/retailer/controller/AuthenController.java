package vn.insee.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.insee.common.Permission;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.UserRepository;
import vn.insee.retailer.common.AppCommon;
import vn.insee.retailer.util.AuthenticationUtils;
import vn.insee.retailer.util.ListUtils;
import vn.insee.retailer.util.RenderUtils;
import vn.insee.retailer.util.StringUtils;
import vn.insee.retailer.wrapper.ZaloService;
import vn.insee.retailer.wrapper.entity.ZaloUserEntity;
import vn.insee.util.HttpUtil;
import vn.insee.util.NoiseUtil;
import vn.insee.util.TimeUtil;
import vn.insee.util.TokenUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AuthenController {
    private static final Logger LOGGER = LogManager.getLogger(AuthenController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/dang-ky", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String register(Authentication authentication,
                           @RequestParam(required = false) String continueUrl,
                           @RequestParam(required = false) String code,
                           @RequestParam(required = false) String src,
                           HttpServletResponse response) throws Exception {
        UserEntity user = AuthenticationUtils.getAuthUser(authentication);
        if (user == null && StringUtils.isEmpty(code)) {
            StringBuilder hookBuilder = new StringBuilder(AppCommon.INSTANCE.getDomain());
            hookBuilder.append("/dang-ky");
            if (!StringUtils.isEmpty(continueUrl)) {
                hookBuilder.append("?continueUrl=");
                hookBuilder.append(continueUrl);
            }
            StringBuilder urlAuthenZaloBuilder = new StringBuilder(AppCommon.INSTANCE.getAuthenZaloUrl());
            urlAuthenZaloBuilder.append("&redirect_uri=");
            urlAuthenZaloBuilder.append(HttpUtil.encodeUrl(hookBuilder.toString()));
            response.sendRedirect(urlAuthenZaloBuilder.toString());
            return "OK";
        }

        if (user == null && !StringUtils.isEmpty(code)) {
            String accessToken = ZaloService.INSTANCE.getAccessToken(code);
            if (StringUtils.isEmpty(accessToken)) {
                response.sendRedirect("/oops");
                return "OK";
            }

            ZaloUserEntity zaloUserEntity = ZaloService.INSTANCE.getUserInfo(accessToken);
            if (zaloUserEntity == null) {
                response.sendRedirect("/oops");
                return "OK";
            }

            user = convert2UserEntity(zaloUserEntity, src);
            if (user == null) {
                response.sendRedirect("/oops");
                return "OK";
            }
            user.setRoleId(Permission.RETAILER.getId());
            //to get user id to store into session
            user = userRepository.saveAndFlush(user);
            String session = TokenUtil.generate(user.getId(), user.getPhone(), 43200000L);
            AuthenticationUtils.writeCookie("_rtl_insee_ss", session, response);
            List<String> lstSession = ListUtils.putWithMaximumSize(session, user.getSessions());
            user.setSessions(lstSession);
            user = userRepository.saveAndFlush(user);

            if (StringUtils.isEmpty(user.getFollowerId())) {
                response.sendRedirect(new StringBuilder("https://zalo.me/")
                        .append(AppCommon.INSTANCE.getOAId()).toString());
                return "OK";
            }
        }

        if (user.getRoleId() == Permission.RETAILER.getId()) {
            if (user.getStatus() == StatusUser.WAIT_COMPLETE_PROFILE) {
                return RenderUtils.render("index.html");
            }else {
                response.sendRedirect("/");
                return "OK";
            }
        }else {
            response.sendRedirect("/oops");
            return "OK";
        }
    }

    private UserEntity convert2UserEntity(ZaloUserEntity zaloUserEntity, String src) {
        UserEntity userEntity = userRepository.findByZaloId(zaloUserEntity.getId());
        if(userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setId(0);
        }
        if (userEntity.getRoleId() == null) {
            userEntity.setRoleId(Permission.ANONYMOUS.getId());
            userEntity.setName(zaloUserEntity.getName());
            userEntity.setZaloId(zaloUserEntity.getId());
            userEntity.setPassword(new String());
            userEntity.setAvatar(zaloUserEntity.getAvatar());
            userEntity.setStatus(StatusUser.WAIT_COMPLETE_PROFILE);
            if (!StringUtils.isEmpty(zaloUserEntity.getBirthday())) {
                userEntity.setBirthday(TimeUtil.getTime(zaloUserEntity.getBirthday()));
            }
        }

        if (StringUtils.isEmpty(src)) {
            try {
                int customerId =  NoiseUtil.de_noiseInt(src);
                UserEntity customer = userRepository.getOne(customerId);
                if (customer != null && customer.getStatus() == StatusUser.WAITING_ACTIVE) {
                    userEntity = link2CustomerProfile(userEntity, customer);
                }
            }catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return userEntity;
    }

    private UserEntity link2CustomerProfile(UserEntity  newUserEntity, UserEntity customer) {
        newUserEntity.setPhone(customer.getPhone());
        newUserEntity.setName(customer.getName());
        newUserEntity.setProducts(customer.getProducts());
        newUserEntity.setCityId(customer.getCityId());
        newUserEntity.setDistrictId(customer.getDistrictId());
        newUserEntity.setAddress(customer.getAddress());
        newUserEntity.setStatus(StatusUser.APPROVED);
        userRepository.delete(customer);
        return newUserEntity;
    }

}
