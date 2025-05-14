package fr.erwil.Spricture.Configuration.Security.UserDetails;

import fr.erwil.Spricture.Application.User.Dtos.Adapters.GetMeAdapter;
import fr.erwil.Spricture.Application.User.Dtos.Responses.GetMeResponseDto;
import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final IUserRepository userRepository;

    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found by email"));
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getStatus().canLogin()
        );

    }

    public CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public GetMeResponseDto getMe() {
        CustomUserDetails userDetails = this.getCurrentUserDetails();
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalStateException("No authenticated user found"));
        return GetMeAdapter.adapt(user);
    }


}
