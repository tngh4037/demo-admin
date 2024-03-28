package com.example.demo.admin.global.config.security.service;

import com.example.demo.admin.domain.admin.domain.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountContext extends User {

    private final Admin account;

    public AccountContext(Admin account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getAdminId(), account.getAdminPwd(), authorities);
        this.account = account;
    }

    public Admin getAccount() {
        return account;
    }
}