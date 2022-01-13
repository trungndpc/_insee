package vn.insee.retailer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.insee.common.Permission;
import vn.insee.common.status.UserStatus;
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
import vn.insee.util.TimeUtil;
import vn.insee.util.TokenUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/dang-ky")
public class AuthenController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/dang-ky", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String register(Authentication authentication,
                           @RequestParam(required = false) String continueUrl,
                           @RequestParam(required = false) String code,
                           HttpServletResponse response) throws Exception {
        UserEntity user = AuthenticationUtils.getAuthUser(authentication);
        if (user == null && StringUtils.isEmpty(code)) {
            StringBuilder hookBuilder = new StringBuilder(AppCommon.INSTANCE.getDomain());
            hookBuilder.append("/dang-ky");
            if (!StringUtils.isEmpty(continueUrl)) {
                hookBuilder.append("?continueUrl=");
                hookBuilder.append(continueUrl);
            }
            StringBuilder urlAuthenZaloBuilder = new StringBuilder(AppCommon.AUTHEN_ZALO_URL);
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

            user = convert2UserEntity(zaloUserEntity);
            if (user == null) {
                response.sendRedirect("/oops");
                return "OK";
            }

            String followerZaloId = user.getFollowerZaloId();
            if (StringUtils.isEmpty(followerZaloId)) {
                response.sendRedirect(new StringBuilder("https://zalo.me/")
                        .append(AppCommon.INSTANCE.getOAId()).toString());
                return "OK";
            }
            user.setRoleId(Permission.RETAILER.getId());

            user = userRepository.saveAndFlush(user);
            String session = TokenUtil.generate(user.getId(), user.getPhone(), 43200000L);
            AuthenticationUtils.writeCookie("_rtl_insee_ss", session, response);
            List<String> lstSession = ListUtils.putWithMaximumSize(session, user.getLstSession());
            user.setLstSession(lstSession);
            user = userRepository.saveAndFlush(user);
        }

        if (user.getRoleId() == Permission.RETAILER.getId()
                && user.getCustomerId() == null
                && user.getStatus() == UserStatus.INIT_FROM_ZALO.getId()) {
            return RenderUtils.render("index.html");
        }

        if (user.getRoleId() != Permission.RETAILER.getId()) {
            response.sendRedirect("/oops");
            return "OK";
        }
        response.sendRedirect("/");
        return "OK";
    }

    private UserEntity convert2UserEntity(ZaloUserEntity zaloUserEntity) {
        UserEntity userEntity = userRepository.findByZaloId(zaloUserEntity.getId());
        if(userEntity == null) {
            userEntity = new UserEntity();
        }
        if (userEntity.getRoleId() == null) {
            userEntity.setRoleId(Permission.ANONYMOUS.getId());
            userEntity.setName(zaloUserEntity.getName());
            userEntity.setZaloId(zaloUserEntity.getId());
            userEntity.setPassword(new String());
            userEntity.setAvatar(zaloUserEntity.getAvatar());
            userEntity.setStatus(UserStatus.INIT_FROM_ZALO.getId());
            userEntity.setEnable(true);
            if (!StringUtils.isEmpty(zaloUserEntity.getBirthday())) {
                userEntity.setBirthday(TimeUtil.getTime(zaloUserEntity.getBirthday()));
            }
        }
        return userEntity;
    }

}
