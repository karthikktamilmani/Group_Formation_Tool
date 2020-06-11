package com.group18.asdc.passwordpolicy;

import com.group18.asdc.errorhandling.PasswordPolicyException;

public class MinlengthPolicy implements IBasePasswordPolicy {

    private static final Integer DEFAULT_MIN_LENGTH = 8;
    private Integer minLength;
    public MinlengthPolicy()
    {
        minLength = DEFAULT_MIN_LENGTH;
    }

    public MinlengthPolicy(Object minLength)
    {
        this.minLength = (Integer)minLength;
    }

    @Override
    public void validate(String password) throws PasswordPolicyException {
        if ( password == null || password.trim().length() < minLength)
        {
            throw new PasswordPolicyException("Password length lesser than "+minLength);
        }
    }
    
}