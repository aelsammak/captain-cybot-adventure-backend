package captain.cybot.adventure.backend.filter;

import captain.cybot.adventure.backend.utility.TokenUtility;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private Algorithm algorithm;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals("/api/v0/login") && !request.getServletPath().equals("/api/v0/users")) {
            TokenUtility.authenticateToken(request, response, algorithm);
        }
        filterChain.doFilter(request, response);
    }
}
