import { Navigate, PathRouteProps } from "react-router-dom";
import { UserDetails } from "./interfaces/authentication-interface";

interface PrivateRouteProps extends PathRouteProps {
  authorities: string[];
  children: React.ReactNode;
  userInfo?: UserDetails;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({
  children,
  authorities,
  userInfo,
  ...rest
}) => {
  if (!children) {
    throw new Error(
      `A component needs to be specified for private route for path ${rest.path}`
    );
  }

  if (!userInfo) {
    return <Navigate to="/" />;
  }

  if (!authorities.includes(userInfo.authorities[0].authority)) {
    return <Navigate to="/login" />;
  }
  return <>{children}</>;
};

export default PrivateRoute;
