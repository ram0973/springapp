import Link from "next/link"
import { Article as ArticleType } from "../../shared/types"
import { Card, Figure, Title, Lead } from "./ArticleCardStyle"

type ArticleProps = {
  article: ArticleType
}

export const ArticleCard = ({ article }: ArticleProps) => {
  return (
    <Link href={`/article/${article.id}`} passHref>
      <Card>
        <Figure>
          <img alt={article.title} src={article.image} />
        </Figure>
        <Title>{article.title}</Title>
        <Lead>{article.excerpt}</Lead>
      </Card>
    </Link>
  )
}
