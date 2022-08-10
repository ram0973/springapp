import Head from "next/head";
import React from "react";

import LoginForm from "../../components/auth/LoginForm";
import Link from "next/link";

const Login = () => (
  <>
  <Head>
    <title>LOGIN</title>
    <meta name="description" content="Login" />
  </Head>
    <div className="bg-white shadow-lg rounded-lg p-8 mr-8">
      <h1 className="font-bold">Login</h1>
      <p className="">
        <Link href="/auth/signup"><a className="block underline my-4">Don't have an account?</a></Link>
      </p>
      <LoginForm />
    </div>
  </>
);

export default Login;