import Router from "next/router";
import React, {EventHandler, FormEvent} from "react";
import { mutate } from "swr";

import UserAPI from "../../api/users";
import ErrorsList, {ValidationError} from "../common/ErrorsList";

const SignupForm = () => {
  const [isLoading, setLoading] = React.useState(false);
  const [message, setMessage] = React.useState("");
  const [errors, setErrors] = React.useState([] as ValidationError[]);
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [rememberMe, setRememberMe] = React.useState(true);

  const handleEmailChange: EventHandler<any> = React.useCallback(
    (e: Event) => setEmail((e.target as HTMLInputElement).value), []
  );
  const handlePasswordChange: EventHandler<any> = React.useCallback(
    (e: Event) => setPassword((e.target as HTMLInputElement).value), []
  );
  const handleRememberMe: EventHandler<any> = React.useCallback(
    (e: Event) => setRememberMe((e.target as HTMLInputElement).checked), []
  );
  const handleSubmit: EventHandler<any> = async (e: Event) => {
    e.preventDefault();
    setLoading(true);

    try {
      console.log(email, password);
      const { data, status } = await UserAPI.signup(
        email,
        password
      );
      if (status !== 200) {
        if (data?.validationErrors) {
          setErrors(data.validationErrors);
          setMessage("");
        } else {
          setMessage(data.message);
        }
      }
      if (rememberMe) {
        window.localStorage.setItem("user", JSON.stringify(data));
      } else {
        window.sessionStorage.setItem("user", JSON.stringify(data));
      }
      await mutate("user", data.user);
      await Router.push("/");
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit}>
        { message !== "" &&
          <div className="mb-6">
            <span className="block text-red-600 mt-2">{ message }</span>
          </div>
        }
        <div className="mb-6">
          <label htmlFor="email" className="block mb-2 text-sm font-semibold text-gray-900 dark:text-gray-300">Your email</label>
          <input
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              type="email"
              placeholder="Email"
              value={email}
              onChange={handleEmailChange}
              required
          />
          <ErrorsList errorsList={errors} fieldName="email"/>
          {/*  {errors['email'] ? <span className="block text-red-600 mt-2">{errors['email']}</span> : ""} */}
        </div>

        <div className="mb-6">
          <label htmlFor="password" className="block mb-2 text-sm font-semibold text-gray-900 dark:text-gray-300">Your password</label>
          <input
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              type="password"
              placeholder="Password"
              value={password}
              onChange={handlePasswordChange}
              required
            />
            <ErrorsList errorsList={errors} fieldName="password"/>
            {/* {errors['password'] ? <span className="block text-red-600 mt-2">{errors['password']}</span> : ""} */}
        </div>

        <div className="flex items-start mb-6">
          <div className="flex items-center h-5">
            <input
              id="remember"
              type="checkbox"
              className="w-4 h-4 bg-gray-50 rounded border border-gray-300 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800"
              checked={rememberMe}
              onChange={handleRememberMe}
            />
          </div>
          <label htmlFor="remember" className="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">Remember me</label>
        </div>
        <button
          className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          type="submit"
          disabled={isLoading}
        >
          Sign up
        </button>
      </form>
    </>
  );
};

export default SignupForm;