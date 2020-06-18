package com.group18.asdc.passwordpolicy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.crypto.Data;

import com.group18.asdc.SystemConfig;
import com.group18.asdc.errorhandling.PasswordPolicyException;
import com.group18.asdc.util.ICustomStringUtils;

public class BasePasswordPolicyManager implements IBasePasswordPolicyManager {

    private ArrayList<HashMap> enabledPasswordPolicies = null;
    private IPasswordPolicyDB passwordPolicyDB = null;
    private ICustomStringUtils customStringUtils = null;

    private enum DatabasePolicyName {
        MIN_LENGTH_POLICY("MinLength"), MAX_LENGTH_POLICY("MaxLength"), MIN_LOWERCASE_POLICY("MinLowercase"),
        MIN_UPPERCASE_POLICY("MinUppercase"), MIN_SPECIALCASE_POLICY("MinSpecialCharacter"),
        CHARACTERS_NOT_ALLOWED("CharactersNotAllowed");

        private final String policyName;

        private DatabasePolicyName(String policyName) {
            this.policyName = policyName;
        }

        @Override
        public String toString() {
            return policyName;
        }

    };

    public BasePasswordPolicyManager(IPasswordPolicyDB passwordPolicyDB, ICustomStringUtils customStringUtils) {
        // load default configurations
        this.passwordPolicyDB = passwordPolicyDB;
        this.customStringUtils = customStringUtils;
    }

    private void loadDefaultConfigurations() {
        //
        if (enabledPasswordPolicies == null) {
            enabledPasswordPolicies = passwordPolicyDB.loadBasePoliciesFromDB();
        }
        //
    }

    @Override
    public void validatePassword(String password) throws PasswordPolicyException {

        loadDefaultConfigurations();

        IBasePasswordPolicy passwordPolicy = null;

        for (HashMap eachEnabledPolicy : enabledPasswordPolicies) {
            //
            String policyName = (String) eachEnabledPolicy.get("POLICY_NAME");
            String policyValue = (String) eachEnabledPolicy.get("POLICY_VALUE");
            if (policyName.equals(DatabasePolicyName.MIN_LENGTH_POLICY.toString())) {
                passwordPolicy = new MinlengthPolicy(policyValue);
            } else if (policyName.equals(DatabasePolicyName.MAX_LENGTH_POLICY.toString())) {
                passwordPolicy = new MaxlengthPolicy(policyValue);
            } else if (policyName.equals(DatabasePolicyName.MIN_LOWERCASE_POLICY.toString())) {
                passwordPolicy = new MinLowercasePolicy(policyValue, customStringUtils);
            } else if (policyName.equals(DatabasePolicyName.MIN_UPPERCASE_POLICY.toString())) {
                passwordPolicy = new MinUppercasePolicy(policyValue, customStringUtils);
            } else if (policyName.equals(DatabasePolicyName.MIN_SPECIALCASE_POLICY.toString())) {
                passwordPolicy = new MinSpecialcharPolicy(policyValue, customStringUtils);
            } else if (policyName.equals(DatabasePolicyName.CHARACTERS_NOT_ALLOWED.toString())) {
                passwordPolicy = new CharsNotAllowedPolicy(policyValue, customStringUtils);
            }
            //
            passwordPolicy.validate(password);

        }

    }

    // private static HashMap<String, String> VALUE_VS_CLASSNAMES_MAP = new
    // HashMap<String, String>();
    // static {
    // VALUE_VS_CLASSNAMES_MAP.put("MinLength", MinlengthPolicy.class.getName());
    // VALUE_VS_CLASSNAMES_MAP.put("MaxLength", MaxlengthPolicy.class.getName());
    // }
    // for (String eachClass : enabledPasswordPolicies) {
    // try {
    //
    // passwordPolicy = (IBasePasswordPolicy)
    // Class.forName(VALUE_VS_CLASSNAMES_MAP.get(eachClass))
    // .getConstructor().newInstance();
    // passwordPolicy.validate(password);
    // } catch (InstantiationException | IllegalAccessException |
    // IllegalArgumentException
    // | InvocationTargetException | NoSuchMethodException | SecurityException
    // | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    //
    
}