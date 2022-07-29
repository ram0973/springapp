import React from "react";
import Head from "next/head";
import { ThemeProvider } from "styled-components";
import { BrowserRouter, Routes, Route } from 'react-router-dom'

import '../styles/globals.css'

import { Header } from "../components/Header";
import { Footer } from "../components/Footer";
import { Center } from "../components/Center";
import { GlobalStyle, theme } from "../shared/theme";
import LoginPage from "../components/Auth/LoginPage";

export default function MyApp({ Component, pageProps }) {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle theme={theme} />
      <Head>
        <title>What's Next?!</title>
      </Head>

      <Header />
      <main className="main">
        <Center>
          <Component {...pageProps} />
        </Center>
      </main>
      <Footer />
    </ThemeProvider>
  );
}

