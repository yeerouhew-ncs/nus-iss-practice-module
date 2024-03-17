import { createContext, useContext } from "react";
import { UserDetails } from "../interfaces/authentication-interface";

interface AuthContextType {
  token: string | null;
  userInfo: UserDetails | undefined;
  user: (userInfo: UserDetails) => void;
  login: (newToken: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuthContext = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuthContext must be used within an AuthProvider");
  }
  return context;
};

export default AuthContext;
