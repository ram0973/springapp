import React, {useEffect, useState} from 'react';
import Link from "next/link";
import { selectAuthState, setAuthState } from "../../store/authSlice";
import { useDispatch, useSelector } from "react-redux";

function handleLogout(event) {
  event.preventDefault();
  console.log('form is submitted');
}

const AuthNav = () => {
  const authState = useSelector(selectAuthState);
  const dispatch = useDispatch();

  return (
      <nav className="bg-white w-full p-2 flex rounded-lg justify-between">
        <div className="flex justify-start">
          <div>
            <div>{authState ? "Logged in" : "Not Logged In"}</div>
            <button
              onClick={() =>
                authState
                  ? dispatch(setAuthState(false))
                  : dispatch(setAuthState(true))
              }
            >
              {authState ? "Logout" : "LogIn"}
            </button>
          </div>
        </div>
        <div className="flex justify-end">
          <Link href={`/auth/login`} className="">
            <button
              className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300
              font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600
              dark:hover:bg-blue-700 dark:focus:ring-blue-800 mr-2"
              type="submit">Login
            </button>
          </Link>
          <Link href={`/auth/logout`} className="">
            <button
              className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300
              font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600
              dark:hover:bg-blue-700 dark:focus:ring-blue-800 mr-2"
              onSubmit={ handleLogout }
              type="submit">Logout
            </button>
          </Link>
          <Link href={`/auth/signup`} className="">
            <button
              className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300
              font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600
              dark:hover:bg-blue-700 dark:focus:ring-blue-800"
              type="submit">Signup
            </button>
          </Link>
        </div>
      </nav>
  )
};

export default AuthNav;
