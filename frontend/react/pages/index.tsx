import React from "react"
import Head from "next/head"
import { Article } from "../shared/types"
import { fetchArticles } from "../api/summary"
import { Section } from "../components/Section"
import { SignupForm } from "../components/SignupForm"

type FrontProps = {
  articles: Article[]
}

export async function getStaticProps() {
  const articles = await fetchArticles()
  return { props: { articles } }
}

export default function Front({ articles }: FrontProps) {
  return (
    <>
      <Head>
        <title>Front page of the Internet</title>
      </Head>
      
      <main>
        <SignupForm></SignupForm>
        <Section articles={articles} />
      </main>
    </>
  )
}
