package ao.it.chandsoft.world.service;

import ao.it.chandsoft.world.domain.UserModel;
import ao.it.chandsoft.world.repository.UtilizadorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    final UtilizadorRepository utilizadorRepository;
    public UserDetailsServiceImpl(UtilizadorRepository utilizadorRepository){
        this.utilizadorRepository = utilizadorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = utilizadorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

        return new User(userModel.getUsername(), userModel.getPassword(), true, true, true,true, userModel.getAuthorities());
    }
}
