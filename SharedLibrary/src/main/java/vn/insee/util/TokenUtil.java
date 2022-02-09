package vn.insee.util;

import io.jsonwebtoken.*;

import java.time.Instant;
import java.util.Date;


public class TokenUtil {

    private static final String ACCESS_TOKEN_KEY = "bmd1eWVuLWRpbmgtdHJ1bmc";
    public static final long MAX_AGE = 1000000000000L;
    public static final long DEFAULT_AGE = 43200000L;

    public static Claims parse(String token) {
        return parseToken(token, ACCESS_TOKEN_KEY);
    }

    private static Claims parseToken(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody();
    }

    public static String generate(int id, String phone, long expiredTime) {
        return Jwts.builder().setId(String.valueOf(id))
                .setAudience(String.valueOf(id))
                .setExpiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + expiredTime)))
                .signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_KEY.getBytes()).compact();
    }

    public static boolean isValid(String token) {
        try {
            parseToken(token, ACCESS_TOKEN_KEY);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException ex) {
            return false;
        }
    }



}
