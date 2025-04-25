package fr.erwil.Spricture.Configuration.Security;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    private final IUserRepository userRepository;

    public UserDetailServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPseudo(username).orElseThrow(() -> new UsernameNotFoundException("User not found by pseudo"));
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return  org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!user.isValidated())
                .build();

    }
}
