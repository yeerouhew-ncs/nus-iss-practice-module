import React, { useState } from "react";
import styles from "./Login.module.scss";
import { Link } from "react-router-dom";
import { UserRole } from "../../../constants/UserRole";
import { Controller, Form, SubmitHandler, useForm } from "react-hook-form";
import { ErrorBar } from "../../../common/error-bar/ErrorBar";
import { IRegisterRequest } from "../../../interfaces/authentication-interface";
import { registerApi } from "../authentication.api";
import AlertPopUp from "../../../common/alert-popup/AlertPopUp";
import { SuccessMessageConstants } from "../../../constants/SuccessMessage";

type FormValues = {
  email: string;
  password: string;
  fullName: string;
  role: string;
};

const Login = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");

  const {
    control,
    formState,
    handleSubmit,
    setValue,
    getValues,
    setError,
    clearErrors,
    register,
    reset,
  } = useForm({
    mode: "onChange",
    defaultValues: {
      email: "",
      password: "",
      fullName: "",
      role: "",
    },
  });

  const switchTab = (isLoginFlag: boolean) => {
    setIsLogin(isLoginFlag);
    reset();
  };

  const registerUser = async (data: FormValues) => {
    console.log(data);
    if (!data.email || !data.fullName || !data.password || !data.role) {
      console.log("hello");
      return;
    }

    const mappingRequest: IRegisterRequest = {
      email: data.email,
      password: data.password,
      fullName: data.fullName,
      subjectRole: data.role,
    };

    try {
      const response = await registerApi(mappingRequest);
      if (response.statusCode === "200" && response.message === "SUCCESS") {
        setSuccessMsg(SuccessMessageConstants.SUCCESS_REGISTER);
        setIsLogin(true);
      }
    } catch (err) {
      setErrorMsg(err as string);
    }
  };

  const onErrors = (error: any) => {
    if (error.email || error.password || error.role || error.fullName) {
      setErrorMsg("Please enter all the fields");
    }
  };

  return (
    <>
      {errorMsg && (
        <AlertPopUp type="danger" message={errorMsg} duration={5000} />
      )}
      {successMsg && (
        <AlertPopUp type="success" message={successMsg} duration={5000} />
      )}
      <div className={styles.loginContainer}>
        <div className={styles.loginMenu}>
          <div className={styles.logo}>
            <img src="./ticket-logo.png" width={"100px"} height={"100px"} />
          </div>
          <div className={styles.formGroup}>
            {!isLogin && (
              <div className={styles.registerForm}>
                <div className={styles.registerInput}>
                  <Controller
                    name="email"
                    control={control}
                    rules={{
                      validate: {},
                      required: "Please enter your email",
                    }}
                    render={({ field }) => (
                      <input
                        {...field}
                        type="text"
                        className="form-control"
                        placeholder="Email"
                        id="email"
                        name="email"
                      />
                    )}
                  />
                  {formState.errors?.email && (
                    <ErrorBar errorMsg={formState.errors.email?.message} />
                  )}
                </div>
                <div className={styles.registerInput}>
                  <Controller
                    name="password"
                    control={control}
                    rules={{
                      validate: {},
                      required: "Please enter your password",
                    }}
                    render={({ field }) => (
                      <input
                        {...field}
                        type="password"
                        className="form-control"
                        placeholder="Password"
                        id="password"
                        name="password"
                      />
                    )}
                  />
                  {formState.errors?.password && (
                    <ErrorBar errorMsg={formState.errors.password?.message} />
                  )}
                </div>
                <div className={styles.registerInput}>
                  <Controller
                    name="fullName"
                    control={control}
                    rules={{
                      validate: {},
                      required: "Please enter your full name",
                    }}
                    render={({ field }) => (
                      <input
                        {...field}
                        type="text"
                        className="form-control"
                        placeholder="Full Name"
                        id="fullName"
                        name="fullName"
                      />
                    )}
                  />
                  {formState.errors?.fullName && (
                    <ErrorBar errorMsg={formState.errors.fullName?.message} />
                  )}
                </div>
                <div className={styles.registerInput}>
                  <Controller
                    name="role"
                    control={control}
                    rules={{
                      required: "Please select your role",
                    }}
                    render={({ field }) => (
                      <select {...field} className="form-control">
                        <option value="" hidden>
                          Select Role
                        </option>
                        <option value="MOP">{UserRole.MOP}</option>
                        <option value="ORGANISER">{UserRole.ORGANISER}</option>
                      </select>
                    )}
                  />
                  {formState.errors?.role && (
                    <ErrorBar errorMsg={formState.errors.role.message} />
                  )}
                </div>
                <button
                  type="submit"
                  onClick={handleSubmit(registerUser, onErrors)}
                >
                  Register
                </button>
                <p className={styles.message}>
                  Do you have an account?{" "}
                  <span onClick={() => switchTab(true)}>Sign In</span>
                </p>
              </div>
            )}

            {isLogin && (
              <div className={styles.loginForm}>
                <input type="text" placeholder="Email" />
                <input type="password" placeholder="Password" />
                <button>Login</button>
                <p className={styles.message}>
                  Don't you have an account?{" "}
                  <span onClick={() => switchTab(false)}>
                    Create an account
                  </span>
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default Login;
