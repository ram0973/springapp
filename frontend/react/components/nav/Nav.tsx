import React from 'react';
import Link from "next/link";

const categories = [{slug: "react", name: "React"}, {slug: "web-dev", name: "Web development"}]

const Nav = () => {
  return (
      <nav className="container mx-auto px-10 mb-8">
        <div className="border-b w-full inline-block border-blue-400 py-8">
          <div className="md:float-left block">
            <Link href="/">
              <span className="cursor-pointer font-bold text-4xl text-white">Hello, world!</span>
            </Link>
          </div>
          <div className="hidden md:float-left md:contents">
            {categories.map((category, index) => (
              <Link key={index} href={`/category/${category.slug}`}><span className="md:float-right mt-2 align-middle text-white ml-4 font-semibold cursor-pointer">{category.name}</span></Link>
            ))}
          </div>
          <Link href={`/auth/login`} className="">
            <span className="transition duration-500 ease transform hover:-translate-y-1 inline-block bg-pink-600 text-lg font-medium rounded-full text-white px-8 py-3 cursor-pointer">Login</span>
          </Link>
        </div>
      </nav>
  )

};

export default Nav;