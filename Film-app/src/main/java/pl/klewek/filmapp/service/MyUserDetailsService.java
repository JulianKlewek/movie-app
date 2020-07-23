package pl.klewek.filmapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.klewek.filmapp.model.User;
import pl.klewek.filmapp.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    public void addUser(User user){
        if(!userRepository.existsAllByUsername(user.getUsername())){
            userRepository.save(user);
        }else{
            System.out.println("Podany login jest zajęty");
        }
    }

}
