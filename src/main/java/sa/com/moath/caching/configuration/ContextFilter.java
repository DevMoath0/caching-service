package sa.com.moath.caching.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContextFilter extends OncePerRequestFilter {

    private void setRequestContext(HttpServletRequest request) {
        String userId = request.getHeader(Header.USER_ID.getKey());
        RequestContext requestContext = ContextHolder.initialize();
        if (userId != null && !userId.isBlank()) {
            requestContext.setUserId(userId);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        setRequestContext(request);
        try {
            filterChain.doFilter(request, response);
        } finally {
            ContextHolder.unset();
        }
    }
}
