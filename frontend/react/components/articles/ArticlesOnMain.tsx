import {Article, ArticlesPaged} from "../../api/types";
import ArticleCard from "./ArticleCard";

export default function ArticlesOnMain({articlesPaged}) {
  const {articles, currentPage, totalItems, totalPages}: ArticlesPaged = articlesPaged;

  return (
    <div className="lg:col-span-8 col-span-1">
      {articles.map((article: Article, index: number) => (
        <ArticleCard key={index} article={article}/>
      ))}
    </div>
  )
}
