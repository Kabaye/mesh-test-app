package com.test.security.service;

import com.test.entity.EmailData;
import com.test.repository.EmailDataRepository;
import com.test.security.model.DefaultUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {
    private final EmailDataRepository emailDataRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmailData emailData = emailDataRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));

        return new DefaultUserDetails()
                .setUsername(emailData.getEmail())
                .setPassword(emailData.getUser().getPassword());
    }
}
