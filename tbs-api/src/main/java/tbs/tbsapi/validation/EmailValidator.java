package tbs.tbsapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {


    public boolean isValid(String email, ConstraintValidatorContext context) {

        String[] parts = email.split("@");

        if (parts.length != 2) {
            return false;
        }

        String prefix = parts[0];
        String domain = parts[1];

        if (!isValidEmailPrefix(prefix) || !isValidEmailDomain(domain)) {
            return false;
        }

        return true;
    }

    private boolean hasConsecutiveSpecialChar(String prefix) {
        return prefix.matches("[._-]{2,}");
    }

    private boolean endsWithSpecialChar(String prefix) {
        return prefix.matches("[._-]$");
    }

    private boolean isValidEmailPrefix(String prefix) {
        if (hasConsecutiveSpecialChar(prefix) || endsWithSpecialChar(prefix)) {
            return false;
        }
        return prefix.matches("[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)*");
    }

    private boolean isValidEmailDomain(String domain) {
        if (!domain.matches("[a-zA-Z0-9.-]+")) return false;

        String[] domainParts = domain.split("\\.");

        if (domainParts.length < 2) return false;

        for (String part : domainParts) {
            if (part.isEmpty() || part.length() > 15 || part.startsWith("-") || part.endsWith("-")) {
                return false;
            }
        }

        String lastDomainPart = domainParts[domainParts.length - 1];
        return lastDomainPart.matches("[a-zA-Z0-9-]+") && lastDomainPart.length() >= 2 && lastDomainPart.length() <= 15;
    }
}
