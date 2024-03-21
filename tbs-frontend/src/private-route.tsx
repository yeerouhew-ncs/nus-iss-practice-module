import { Navigate, Outlet, PathRouteProps } from "react-router-dom";
import { UserDetails } from "./interfaces/authentication-interface";
import { useAuthContext } from "./context/AuthContext";

const PrivateRoute = (props: any) => {
  const { token, userInfo } = useAuthContext();

  return token ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;

// interface PrivateRouteProps extends PathRouteProps {
//   authorities: string[];
//   children: React.ReactNode;

// }

// const PrivateRoute: React.FC<PrivateRouteProps> = ({
//   children,
//   authorities,
//   ...rest
// }) => {
//   const { userInfo, token } = useAuthContext();

//   if (!children) {
//     throw new Error(
//       `A component needs to be specified for private route for path ${rest.path}`
//     );
//   }

//   if (!token) {
//     return <Navigate to="/login" />;
//   }

//   if (userInfo && !authorities.includes(userInfo.authorities[0].authority)) {
//     return <Navigate to="/login" />;
//   }
//   return <>{children}</>;
// };

// export default PrivateRoute;
