package com.example.demo.admin.global.config.security.service;

import com.example.demo.admin.domain.admin.domain.Admin;
import com.example.demo.admin.domain.admin.repository.AdminRepository;
import com.example.demo.admin.global.util.MessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminId(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageHelper.getMessage("login.invalid.member.not.match")));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(admin.getAdminAuth().getCode()));

        return new AccountContext(admin, roles);
    }
}
