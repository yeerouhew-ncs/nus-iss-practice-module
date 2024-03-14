import { Navigate, PathRouteProps } from "react-router-dom";
import { useAuthContext } from "./context/AuthContext";

interface PrivateRouteProps extends PathRouteProps {
  authorities: string[];
  children: React.ReactNode;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({
  children,
  authorities,
  ...rest
}) => {
  const { userInfo } = useAuthContext();

  if (!children) {
    throw new Error(
      `A component needs to be specified for private route for path ${rest.path}`
    );
  }

  if (!userInfo) {
    return <Navigate to="/" />;
  }

  if (authorities.includes(userInfo.authorities[0].authority)) {
    return <>{children}</>;
  }

  return <Navigate to="/login" />;
};

export default PrivateRoute;
