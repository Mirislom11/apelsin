package com.company.utils;

import com.company.exceptions.ForbiddenException;
import com.company.model.enums.ProfileRole;
import com.company.model.vm.AuthVM;
import com.company.model.vm.ProfileJwtVm;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {
    private static final String secretKey = "secretKey";
    public static String createJwt (long id, ProfileRole profileRole) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.setIssuer("apelsin");
        jwtBuilder.claim("role", profileRole.name());
        String jwt = jwtBuilder.compact();
        return jwt;
    }
    public static ProfileJwtVm getProfile (HttpServletRequest request) {
        ProfileJwtVm jwtDTO = (ProfileJwtVm) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new ForbiddenException("Your jwt Null");
        }

        return jwtDTO;
    }

    public static ProfileJwtVm getProfile (HttpServletRequest request, ProfileRole role) {
        ProfileJwtVm jwtDTO = (ProfileJwtVm) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new ForbiddenException("Your jwt Null");
        }
        if (!role.equals(jwtDTO.getRole())) {
            throw new ForbiddenException("You are not allowed to use this feature");
        }
        return jwtDTO;
    }

    public static ProfileJwtVm decodeJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);
        Claims claims = (Claims)  jws.getBody();
        String id = claims.getSubject();
        ProfileRole profileRole = ProfileRole.valueOf((String) claims.get("role"));
        return new ProfileJwtVm(Integer.parseInt(id), profileRole);
    }
}
