package com.timeOrganizer.security;

import com.timeOrganizer.model.dto.mappers.UserMapper;
import com.timeOrganizer.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    public LoggedUser loadById(long id) throws EntityNotFoundException{
        return userMapper.toLoggedUser(userRepository.findById(id).orElseThrow(()->new EntityNotFoundException(String.valueOf(id))));
    }
    @Override
    public LoggedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.toLoggedUser(userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email)));
    }
}
