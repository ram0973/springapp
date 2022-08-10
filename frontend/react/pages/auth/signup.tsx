import Head from "next/head";
import React from "react";

import SignupForm from "../../components/auth/SignupForm";
import Link from "next/link";

const Register = () => (
  <>
  <Head>
    <title>REGISTER</title>
    <meta name="description" content="Please register before login" />
  </Head>
    <h1 className="font-bold">Sign Up</h1>
    <p className="">
      <Link href="/auth/login"><a className="block underline my-4">Have an account?</a></Link>
    </p>
    <SignupForm />
  </>
);

export default Register;