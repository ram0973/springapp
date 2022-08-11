import {Article} from "../../api/types";

export const ArticleBody = ({article}: Article) => {
  return (
  <div className="bg-white shadow-lg rounded-lg p-8 pb-12 mb-8">
    <div className="text-xl mb-8 font-semibold border-b pb-4">{article.title}</div>
    <div className="text-xl mb-8 border-b pb-4">{article.content}</div>
  </div>
  )
}