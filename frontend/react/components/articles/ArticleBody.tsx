import {Article} from "../../api/types";

export const ArticleBody = (article: Article) => {

  return (<div>{ article.title }</div>
  )
}