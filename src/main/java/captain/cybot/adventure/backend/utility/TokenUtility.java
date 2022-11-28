package captain.cybot.adventure.backend.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@UtilityClass
@Slf4j
public class TokenUtility {

    public void createAccessToken(String username, List<String> roles, Map<String, String> tokens, Algorithm algorithm) {
        String access_token = JWT.create()
                .withSubject(username)
                .withExpiresAt(Date.from(Instant.now().plus(48, ChronoUnit.HOURS)))
                .withIssuer("SYSC_4907_GROUP_1")
                .withClaim("roles", roles)
                .sign(algorithm);
        tokens.put("username", username);
        tokens.put("access_token", access_token);
    }

    public void authenticateToken(HttpServletRequest request, HttpServletResponse response, Algorithm algorithm) throws IOException {
        try {
            DecodedJWT decodedJWT = getDecodedToken(request, response, algorithm);
            String username = getUsernameFromToken(decodedJWT);
            if (!request.getRequestURI().contains(username) && request.getRequestURI().contains("/user")){
                throw new Exception("User not authorized");
            }
            String[] roles = getRolesFromToken(decodedJWT);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            errorMsg(response, e);
        }
    }

    public DecodedJWT getDecodedToken(HttpServletRequest request, HttpServletResponse response, Algorithm algorithm) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                JWTVerifier verifier = JWT.require(algorithm).build();
                return verifier.verify(token);
            } catch (Exception e) {
                errorMsg(response, e);
            }
        }
        return null;
    }

    private DecodedJWT getDecodedToken(String authHeader, Algorithm algorithm) {
        try {
            String token = authHeader.substring("Bearer ".length());
            System.out.println(token);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getUsernameFromToken(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public String[] getRolesFromToken(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("roles").asArray(String.class);
    }

    public static void errorMsg(HttpServletResponse response, Exception e) throws IOException {
        log.error("Error logging in: {}", e.getMessage());
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_msg", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
