package IvanovVasil.OrderManagmentSystem.security;

import IvanovVasil.OrderManagmentSystem.User.User;
import IvanovVasil.OrderManagmentSystem.User.UsersRepository;
import IvanovVasil.OrderManagmentSystem.User.UsersService;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import IvanovVasil.OrderManagmentSystem.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
  @Autowired
  private JWTTools jwtTools;

  @Autowired
  private UsersRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException("Athorization Bearer token not found.");
    } else {
      String token = authHeader.substring(7);
      jwtTools.verifyToken(token);
      String id = jwtTools.extractIdFroToken(token);
      User currentUser = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException(id));
      Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    return pathMatcher.match("/authentication/**", request.getRequestURI())
            || pathMatcher.match("/websocket/**", request.getRequestURI());
  }
}
