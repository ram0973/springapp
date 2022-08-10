import React, {useEffect, useState} from "react";
import {Article, ArticlesPaged} from "../api/types";

import ArticleAPI from "../api/articles";
import {GetServerSideProps} from "next";
import ArticleCard from "../components/articles/ArticleCard";
import ArticlesPagination from "../components/articles/ArticlesPagination";
import ArticlesOnMain from "../components/articles/ArticlesOnMain";
import {useRouter} from "next/router";

export default function IndexPage() {
  let router = useRouter();
  let id = Number(router.query["page"]);
  console.log(id);
  let articlesPaged: ArticlesPaged = await ArticleAPI.articles(id).then((res) => res);
  //console.log(articlesPaged)
  return (<></>);
  {/*<ArticlesOnMain articlesPaged = {articlesPaged} />*/}

}


