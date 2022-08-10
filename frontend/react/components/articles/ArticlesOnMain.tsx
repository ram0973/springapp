import {Article, ArticlesPaged} from "../../api/types";
import ArticleCard from "./ArticleCard";
import ArticlesPagination from "./ArticlesPagination";
import {GetServerSideProps} from "next";
import ArticleAPI from "../../api/articles";
import {useEffect, useState} from "react";
import {useRouter} from "next/router";

export default function ArticlesOnMain(articlesPaged: ArticlesPaged) {
  return (
    <div className="lg:col-span-8 col-span-1">
      {articlesPaged.articles.map((article: Article, index: number) => (
        <ArticleCard key={index} article={article}/>
      ))}
      <ArticlesPagination {...articlesPaged}  />
    </div>)
}
