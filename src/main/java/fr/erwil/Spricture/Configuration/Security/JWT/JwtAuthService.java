package fr.erwil.Spricture.Configuration.Security.JWT;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Configuration.FrontendProperties;
import fr.erwil.Spricture.Configuration.Security.Dtos.LoginDto;
import fr.erwil.Spricture.Configuration.Security.IAuthService;
import fr.erwil.Spricture.Exceptions.BaseException;
import fr.erwil.Spricture.Tools.Mail.MailService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtAuthService implements IAuthService {


    private static final Logger logger = LogManager.getLogger(JwtAuthService.class);
    private final AuthenticationManager authenticationManager;

    private  final IUserRepository userRepository;

    private final IJwtTokenProvider jwtProvider;

    private final MailService mailService;
    private final FrontendProperties frontendProperties;

    public JwtAuthService(AuthenticationManager authenticationManager, IUserRepository userRepository, IJwtTokenProvider jwtProvider, MailService mailService, FrontendProperties frontendProperties) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.mailService = mailService;
        this.frontendProperties = frontendProperties;
    }

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateLoginToken(authentication.getName());
    }
    @Transactional
    @Override
    public boolean verify(String token) {

        if(!jwtProvider.validateToken(token)){
            throw new BadCredentialsException("Invalid Token");
        }

        String email = jwtProvider.extractUserName(token);
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new BadCredentialsException("No user found by email for email validation.");
        }
        if(user.get().isEmailValidated()){
            return true;
        }
        user.get().setEmailValidated(true);
        userRepository.save(user.get());

        return true;
    }

    @Override
    public void sendVerificationEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new BaseException(HttpStatus.UNAUTHORIZED, "The user does not exists");
        }

        if(user.get().isEmailValidated()){
            throw new BaseException(HttpStatus.UNAUTHORIZED, "The user is already validated");
        }

        String token = jwtProvider.generateVerifyToken(email);

        URI verificationLink = this.getTokenToUriFunction().apply(token);
        mailService.sendSimpleMessage(email,"Validate your email",verificationLink.toString());


    }

    /**
     * Builds the public URL (front-end) that the user will click to validate the token.
     */
    private Function<String, URI> getTokenToUriFunction() {
        return token -> {
            String base = frontendProperties.getHost();
            if (!base.endsWith("/")) base += "/"; //clean up the host

            String path = frontendProperties.getVerifyAccount();
            if (path.startsWith("/")) path = path.substring(1); //cleanup the path

            String encoded = URLEncoder.encode(token, StandardCharsets.UTF_8);
            return URI.create(base + path + "?token=" + encoded);
        };
    }

}
