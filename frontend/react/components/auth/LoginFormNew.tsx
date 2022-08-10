import { useRouter } from 'next/router';
import React, {useRef, useState} from "react";

import UserAPI from "../../api/users";
import ErrorsList from "../common/ErrorsList";
import {LoginFormData} from "../../api/types";

const LoginForm = () => {
  const router = useRouter();
  const form = useRef(null);
  const [user, setUser] = useState();
  const errors = {}

  const handleSubmit = (e: Event) => {
    try {
      e.preventDefault();
      const data: LoginFormData = new FormData(e.target as HTMLFormElement) as LoginFormData;
      console.log(data.email, data.password);
        UserAPI.login(
          data.email,
          data.password
        ).then(r => r);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
        <form onSubmit={handleSubmit}>
          { message !== "" &&
          <div className="mb-6">
            <span className="block text-red-600 mt-2"> message </span>
          </div>
          }
          <div className="mb-6">
            <label htmlFor="email" className="block mb-2 text-sm font-semibold text-gray-900 dark:text-gray-300">Your email</label>
            <input
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                type="email"
                placeholder="Email"
                name="email"
                required
            />
            <ErrorsList errorsList={errors} fieldName="email"/>
            {/* {errors['email'] ? <span className="block text-red-600 mt-2">{errors['email']}</span> : ""} */}
          </div>

          <div className="mb-6">
            <label htmlFor="password" className="block mb-2 text-sm font-semibold text-gray-900 dark:text-gray-300">Your password</label>
            <input
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                type="password"
                placeholder="Password"
                name="password"
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
                checked=true
                name="remember"
              />
            </div>
            <label htmlFor="remember" className="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">Remember me</label>
          </div>
          <button
            className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            type="submit"
          >
            Login
          </button>
        </form>

    </>

};

export default LoginForm;
