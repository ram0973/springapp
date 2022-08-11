import '../styles/globals.css'
import {AppProps} from 'next/app';
import ArticlesWidget from "../components/articles/ArticlesOnAside";
import Nav from "../components/nav/Nav";
import AuthNav from "../components/auth/AuthNav";

export default function NextApp({Component, pageProps}: AppProps) {
  return (
    <div className="container mx-auto px-10">
      {/*<p>{ JSON.parse(window.localStorage.getItem('user')).email }</p>*/}
      <AuthNav/>
      <Nav/>
      <main className="flex w-full flex-col md:flex-row flex-wrap md:flex-nowrap py-4 flex-grow">
        <article className="layout g-white shadow-lg rounded-lg p-0 mr-8 w-full md:w-2/3">
          <Component {...pageProps} />
        </article>
        <aside className="layout bg-white shadow-lg rounded-lg p-0 w-full md:w-1/3">
          <ArticlesWidget/>
        </aside>
      </main>
      <footer>Made with Java - Spring - React - NextJS</footer>
    </div>
  )
}