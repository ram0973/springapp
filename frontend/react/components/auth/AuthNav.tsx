import React, {useEffect, useState} from 'react';
import Link from "next/link";

function handleLogout(event) {
  event.preventDefault();
  console.log('form is submitted');
}

const AuthNav = () => {
  const [user, setUser] = useState("");

  useEffect(() => {
    let email = JSON.parse(localStorage.getItem("user"))?.email
    setUser(email);
  }, [])

  return (
    <nav className="bg-white w-full p-2 flex rounded-lg justify-between">
      <div className="flex justify-start">
        <div>{JSON.stringify(user)}</div>
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
