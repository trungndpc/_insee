package vn.insee.retailer.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.service.BroadcastService;
import vn.insee.retailer.util.AuthenticationUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(3)
public class TrackingClickBroadcastFilter implements Filter {

    @Autowired
    private BroadcastService broadcastService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String strBrcId = req.getParameter("brcId");
        if (strBrcId != null && !strBrcId.isEmpty()) {
            int brdId = getBrdId(req);
            if (brdId >= 0) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserEntity user = AuthenticationUtils.getAuthUser(authentication);
                if (user != null) {
                    broadcastService.trackingClick(user.getId(), brdId);
                }
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private int getBrdId(HttpServletRequest req) {
        try{
            String s = req.getParameter("brcId");
            if (s != null || !s.isEmpty()) {
                return Integer.parseInt(s);
            }
        }catch (Exception e) {

        }
        return -1;
    }

}
