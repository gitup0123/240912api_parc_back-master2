package com.parc.api.security;

import com.parc.api.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final SecurityService securityService;

    public UserAuthorizationManager(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Integer userId = Integer.valueOf(context.getRequest().getParameter("id"));
        Authentication auth = authentication.get();
        return new AuthorizationDecision(securityService.isCurrentUserOrAdmin(userId, auth));
    }
}


