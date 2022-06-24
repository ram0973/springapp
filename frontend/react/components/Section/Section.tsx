import { Article as ArticleType } from "../../shared/types"
import { ArticleCard } from "../Article"
import { Grid, Title } from "./style"

type SectionProps = {
  articles: ArticleType[]
}

export const Section = ({ articles }: SectionProps) => {
  return (
    <section>
      <Grid>
        {articles.map((article) => (
          <ArticleCard key={article.id} article={article} />
        ))}
      </Grid>
    </section>
  )
}
