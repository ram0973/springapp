import { GetStaticProps } from "next"
import { useRouter } from "next/router"
import { fetchArticle } from "../../api/article"
import { Article as ArticleType } from "../../shared/types"
import { Loader } from "../../components/Loader"
import { articlePaths as paths } from "../../shared/staticPaths"
import { ArticleBody } from "../../components/Article/ArticleBody"

type ArticleProps = {
  article: ArticleType
}

export const getStaticProps: GetStaticProps<ArticleProps> = async ({
  params
}) => {
  if (typeof params.id !== "string") throw new Error("Unexpected id")
  const article = await fetchArticle(params.id)
  return { props: { article } }
}

export async function getStaticPaths() {
  return { paths, fallback: true }
}

const Article = ({ article }: ArticleProps) => {
  const router = useRouter()

  if (router.isFallback) return <Loader />
  return <ArticleBody article={article} />
}

export default Article
