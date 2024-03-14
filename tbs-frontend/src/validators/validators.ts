export const isValidEmail = (email: string): boolean => {
  const hasConsecutiveSpecialChar = (prefix: string) => {
    return prefix.match("[._-]{2,}");
  };
  const endsWithSpecialChar = (prefix: string) => {
    return prefix.match("[._-]$");
  };
  const isValidEmailPrefix = (prefix: string) => {
    if (hasConsecutiveSpecialChar(prefix) || endsWithSpecialChar(prefix)) {
      return false;
    }
    return prefix.match("[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)*");
  };
  const isValidEmailDomain = (domain: string) => {
    if (!domain.match("[a-zA-Z0-9.-]+")) return false;

    const domainParts = domain.split(".");
    if (domainParts.length < 2) return false;

    for (const part of domainParts) {
      if (
        part.length === 0 ||
        part.length > 15 ||
        part.startsWith("-") ||
        part.endsWith("-")
      ) {
        return false;
      }
    }

    const lastDomainPart = domainParts[domainParts.length - 1];
    return (
      lastDomainPart.match("[a-zA-Z0-9-]+") &&
      lastDomainPart.length >= 2 &&
      lastDomainPart.length <= 15
    );
  };

  const parts = email.split("@");

  if (parts.length !== 2) {
    return false;
  }

  const prefix = parts[0];
  const domain = parts[1];

  if (!isValidEmailPrefix(prefix) || !isValidEmailDomain(domain)) {
    return false;
  }

  return true;
  // const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
  // return emailRegex.test(email);
};
