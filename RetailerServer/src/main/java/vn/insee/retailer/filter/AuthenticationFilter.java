package vn.insee.retailer.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.insee.jpa.repository.UserRepository;
import vn.insee.retailer.common.BaseResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.insee.retailer.common.UserStatus;
import vn.insee.retailer.security.InseeUserDetail;
import vn.insee.retailer.security.InseeUserDetailService;
import vn.insee.util.HttpUtil;
import vn.insee.util.TokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Order(2)
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    @Autowired
    private InseeUserDetailService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest req,
                                 @NonNull HttpServletResponse resp,
                                 @NonNull FilterChain chain) throws IOException, ServletException {
        try {
            String _inseeSS = getSession(req);
            if (_inseeSS != null) {
                Claims claims = TokenUtil.parse(_inseeSS);
                int userId = Integer.parseInt(claims.getAudience());
                UserDetails userDetail = userService.loadUserById(userId);
                InseeUserDetail inseeUser = (InseeUserDetail) userDetail;
                if (inseeUser.getUser().getStatus() == UserStatus.DISABLED) {
                    throw new Exception(String.format("User is disable | userId: %d", userId));
                }
                List<String> lstSession = inseeUser.getUser().getSessions();
                if (lstSession.contains(_inseeSS)) {
                    UsernamePasswordAuthenticationToken auth
                            = new UsernamePasswordAuthenticationToken(userDetail,
                            userDetail.getPassword(), userDetail.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.clearContext();
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }   catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            resp.setHeader("Content-Type", "application/json");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setError(HttpStatus.UNAUTHORIZED.value());
            resp.getWriter().println(objectMapper.writeValueAsString(baseResponse));
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return false;
    }

    private String getSession(HttpServletRequest req) {
        return HttpUtil.getCookie("_rtl_insee_ss", req);
    }

}
