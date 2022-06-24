import Link from "next/link"
import { Article } from "../../shared/types"
import { Title, Figure, Content, Meta } from "./ArticleBodyStyle"

type ArticleBodyProps = {
  article: Article
}

export const ArticleBody = ({ article }: ArticleBodyProps) => {
  return (
    <div>
      <Title>{article.title}</Title>
      <Figure>
        <img src={article.image} alt={article.title} />
      </Figure>

      <Content dangerouslySetInnerHTML={{ __html: article.content }} />

      <Meta>
        <span>{article.dateCreated}</span>
        <span>&middot;</span>
        <span>&middot;</span>
      </Meta>
    </div>
  )
}
