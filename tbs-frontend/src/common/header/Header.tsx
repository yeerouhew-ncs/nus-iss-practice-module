import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import styles from "./Header.module.scss";
import { Link, useNavigate } from "react-router-dom";
import { useAuthContext } from "../../context/AuthContext";

const Header: React.FC = () => {
  const navigate = useNavigate();

  const { logout } = useAuthContext();

  const logOut = () => {
    logout();
    navigate("/");
  };

  return (
    <div className={styles.headerContainer}>
      <div className={styles.headerNav}>
        <div className={styles.headerWrapper}>
          <a href="" title="Ticket Booking System">
            <img
              src="./ticket-logo.png"
              alt="tbs-logo"
              className={styles.tbsLogo}
            />
          </a>
        </div>
        <div className={styles.navBar}>
          <div className={styles.navContainer}>
            <Link to="/event/list" className={styles.navLink}>
              <FontAwesomeIcon icon={faHome} className={styles.navIcon} />
              <span className={styles.navLabel}>Event</span>
            </Link>
          </div>
          <div className={styles.navContainer}>
            <Link to="/plan/list" className={styles.navLink}>
              <FontAwesomeIcon icon={faHome} className={styles.navIcon} />
              <span className={styles.navLabel}>Plan</span>
            </Link>
          </div>
        </div>
      </div>

      <div>
        <button
          type="button"
          onClick={logOut}
          className={`${styles.logoutBtn}`}
        >
          Logout
        </button>
      </div>
    </div>
  );
};

export default Header;
