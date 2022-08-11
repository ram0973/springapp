import {GetServerSidePropsContext} from "next"
import {Article} from "../../api/types"
import {ArticleBody} from "../../components/articles/ArticleBody";

export default function ArticleById(article: Article) {
  return <ArticleBody {...article}/>
}

const fetcher = (url: string, init?: RequestInit) => fetch(url, init).then((res: Response) => res.json())

export async function getServerSideProps(context: GetServerSidePropsContext) {
  const id: number = Number(context.query.id);
  const res = await fetch(`http://localhost:8080/api/articles/${id}`)
  const article = await res.json()
  return {props: {article}};
}
