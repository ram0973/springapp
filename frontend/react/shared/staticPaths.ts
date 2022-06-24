import { EntityId } from "./types"

type ArticleStaticParams = {
  id: EntityId
}

type ArticleStaticPath = {
  params: ArticleStaticParams
}

const staticArticlesIdList: EntityId[] = [1, 2, 3]

export const articlePaths: ArticleStaticPath[] = staticArticlesIdList.map(
  (id) => ({
    params: { id: String(id) }
  })
)
