package fr.erwil.Spricture.Configuration.Security.JWT;

import fr.erwil.Spricture.Configuration.Security.Dtos.LoginDto;
import fr.erwil.Spricture.Configuration.Security.IAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtAuthService implements IAuthService {


    private AuthenticationManager authenticationManager;

    private IJwtTokenProvider jwtProvider;

    // TODO : Implement the login method
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }
}
