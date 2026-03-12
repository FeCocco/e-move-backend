package com.fegcocco.emovebackend.service;

import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Service
public class EmailDomainValidationService {

    public boolean hasMxRecord(String email) {
        try {
            String domain = extractDomain(email);
            if (domain == null || domain.isBlank()) {
                return false;
            }

            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

            InitialDirContext context = new InitialDirContext(env);
            Attributes attributes = context.getAttributes(domain, new String[]{"MX"});
            Attribute mx = attributes.get("MX");

            if (mx == null) {
                return false;
            }

            NamingEnumeration<?> servers = mx.getAll();
            return servers.hasMore();
        } catch (Exception e) {
            return false;
        }
    }

    private String extractDomain(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex < 0 || atIndex == email.length() - 1) {
            return null;
        }
        return email.substring(atIndex + 1);
    }
}