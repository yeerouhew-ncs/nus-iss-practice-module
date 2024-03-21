import { ReactNode, useState } from "react";
import AuthContext from "./AuthContext";
import { UserDetails } from "../interfaces/authentication-interface";

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [token, setToken] = useState<string | null>(
    localStorage.getItem("token")
  );

  const [userInfo, setUserInfo] = useState<UserDetails | undefined>();

  const login = (newToken: string) => {
    setToken(newToken);
    localStorage.setItem("token", newToken);
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem("token");
  };

  const user = (userInfo: UserDetails) => {
    console.log("PROVIDER USERINFO", userInfo);
    setUserInfo(userInfo);
  };

  return (
    <AuthContext.Provider value={{ token, userInfo, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
