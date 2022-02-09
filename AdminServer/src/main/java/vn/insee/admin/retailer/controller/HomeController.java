package vn.insee.admin.retailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vn.insee.admin.retailer.controller.converter.UserConverter;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.admin.retailer.util.RenderUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class HomeController {
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        return RenderUtils.render("index.html");
    }


}
