import fetch from "node-fetch"
import { Article, EntityId } from "../shared/types"
import { config } from "./config"

export async function fetchArticle(id: EntityId): Promise<Article> {
  const res = await fetch(`${config.baseUrl}/articles/${id}`)
  return await res.json()
}
