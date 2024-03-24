import React, { useEffect, useState } from "react";
import { getPlanListApi } from "../../plan.api";
import { PlanList } from "../../../../interfaces/seating-plan-interface";
import { Link, useNavigate } from "react-router-dom";
import styles from "./OrganiserPlan.module.scss";
import { useAuthContext } from "../../../../context/AuthContext";

const OrganiserPlan: React.FC = () => {
  const [planList, setPlanList] = useState<PlanList[]>([]);

  const navigate = useNavigate();
  const { userInfo } = useAuthContext();

  useEffect(() => {
    const getPlanList = async () => {
      const response = await getPlanListApi();

      try {
        if (response.message === "SUCCESS" && response.statusCode === "200") {
          setPlanList(response.seatingPlanList);
        }
      } catch (error) {
        // TODO: error handling
        console.log(error);
      }
    };

    getPlanList();
  }, []);

  // const redirectCreateOnClick = () => {
  //   navigate("/organ/plan/create");
  // };

  const redirectEditOnClick = (id: string) => {
    navigate(`/organiser/plan/edit-category/${id}`);
  };

  return (
    <div>
      <div className={`${styles.planHeader}`}>
        <div>
          <h2>Seating Plans</h2>
        </div>
        {/* <div>
          <button
            type="button"
            className={`btn ${styles.primaryBtn} btn-sm ${styles.btnMarginRight}`}
            onClick={redirectCreateOnClick}
            hidden={userInfo?.authorities[0].authority === "ORGANISER"}
          >
            <span>Create Plan</span>
          </button>
        </div> */}
      </div>

      <div className="searchResultTable card">
        <div className={`card-header ${styles.tableTitleBlue}`}>
          <span>Seating Plans</span>
        </div>
        {planList && planList.length > 0 && (
          <div className="card-block" style={{ padding: "1.5%" }}>
            <div className="table-responsive">
              <table className="table table-striped">
                <thead>
                  <tr>
                    <th>S/N</th>
                    <th>Plan Name</th>
                    <th>Venue</th>
                  </tr>
                </thead>
                <tbody>
                  {planList &&
                    planList.length > 0 &&
                    planList.map((plan, index) => (
                      <tr key={index}>
                        <td>{index + 1}</td>
                        <td>
                          <div>
                            <Link to={`/organiser/plan/view/${plan.planId}`}>
                              {plan.planName}
                            </Link>
                          </div>
                        </td>
                        <td>
                          <div>{plan.venueName}</div>
                        </td>
                        <td>
                          <div>
                            <button
                              type="button"
                              className={`btn btn-sm ${styles.primaryBtn} ${styles.btnMarginRight}`}
                              onClick={() => redirectEditOnClick(plan.planId)}
                            >
                              Edit
                            </button>
                          </div>
                        </td>
                        {/* <td>
                    <div>
                      <button
                        type="button"
                        className={`${styles.primaryBtn} ${styles.btnMarginRight}`}
                      >
                        Delete
                      </button>
                    </div>
                  </td> */}
                      </tr>
                    ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {!planList ||
          (planList.length === 0 && (
            <div className={`${styles.emptyTable}`}>No seating plan found</div>
          ))}
      </div>
    </div>
  );
};

export default OrganiserPlan;
