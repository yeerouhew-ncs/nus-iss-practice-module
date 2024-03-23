import React, { Dispatch, SetStateAction } from "react";
import { Button, Dropdown } from "react-bootstrap";
import styles from "./AdminHeader.module.scss";
import { Link, Outlet, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { useAuthContext } from "../../context/AuthContext";
import { UserDetails } from "../../interfaces/authentication-interface";

interface LoginProps {
  setAuth: (auth: boolean) => void;
  setUser: Dispatch<SetStateAction<UserDetails | undefined>>;
}

const AdminHeader: React.FC<LoginProps> = ({ setAuth, setUser }) => {
  const imageBasePath =
    window.location.protocol + "//" + window.location.host + "/";

  const { userInfo } = useAuthContext();

  const handleLogout = () => {
    localStorage.removeItem("token");
    setAuth(false);
    setUser(undefined);
  };

  // if (!userInfo) {
  //   return null;
  // }

  return (
    <>
      <div>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            marginTop: "0vh",
            marginBottom: "1.5em",
            // padding: "3%",
          }}
        >
          <div style={{ marginLeft: "2vw" }}>
            <strong style={{ fontSize: "1.8rem" }}>
              <img
                src={`${imageBasePath + "ticket-logo.png"}`}
                alt="tbs-logo"
                className={styles.tbsLogo}
              />
            </strong>
          </div>
          <div
            style={{
              display: "flex",
              flexWrap: "wrap",
              justifyContent: "space-around",
              alignItems: "center",
            }}
          >
            <Link to={"/admin/event/list"} className={`${styles.underLine2}`}>
              <Button variant="text" color="default">
                EVENT
              </Button>
            </Link>
            <Link to={"/admin/plan/list"} className={`${styles.underLine2}`}>
              <Button variant="text" color="default">
                SEATING PLAN
              </Button>
            </Link>
            <Link to={"/admin/plan/list"} className={`${styles.underLine2}`}>
              <Button variant="text" color="default">
                USER
              </Button>
            </Link>

            <Dropdown>
              <Dropdown.Toggle
                className={`${styles.userDropDownToggle}`}
                // id="dropdown-basic"
              >
                <FontAwesomeIcon
                  icon={faUser}
                  className={styles.navIcon}
                  style={{ width: "1em", height: "1em" }}
                />
              </Dropdown.Toggle>

              <Dropdown.Menu>
                <div style={{ fontSize: "14px", padding: "4px 16px" }}>
                  Welcome, {userInfo?.email}
                </div>
                <Dropdown.Divider />
                <Dropdown.Item onClick={handleLogout}>Logout</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </div>
        </div>
      </div>
      <div>
        <Outlet />
      </div>
    </>
  );
};

export default AdminHeader;
