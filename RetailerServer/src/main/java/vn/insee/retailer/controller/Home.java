package vn.insee.retailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.common.Permission;
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.service.CustomerService;
import vn.insee.retailer.util.AuthenticationUtils;
import vn.insee.retailer.util.RenderUtils;
import vn.insee.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/")
public class Home {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(Authentication auth, HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        UserEntity authUser = AuthenticationUtils.getAuthUser(auth);
        if (authUser == null) {
            String continueUrl = HttpUtil.getFullURL(request);
            response.sendRedirect("/dang-ky?continueUrl=" + continueUrl);
            return "OK";
        }
        if (!isRetailer(authUser)) {
            response.sendRedirect("/oops");
            return "OK";
        }
        CustomerEntity customerEntity = customerService.getById(authUser.getCustomerId());
        String jsonCustomer = objectMapper.writeValueAsString(customerEntity);
        return RenderUtils.render("index.html", "customer", jsonCustomer);
    }

    private boolean isRetailer(UserEntity userEntity) {
        return userEntity.getRoleId() == Permission.RETAILER.getId();
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello module";
    }
}
