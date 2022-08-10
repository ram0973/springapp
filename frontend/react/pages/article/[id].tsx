import {GetServerSideProps} from "next"
import {useRouter} from "next/router"
import {Article} from "../../api/types"
import ArticleAPI from "../../api/articles";
import {ArticleBody} from "../../components/articles/ArticleBody";

export default function ArticleById(article: Article) {
  return <ArticleBody {...article}  />
}

export const getServerSideProps: GetServerSideProps = async (context) => {
  const id: number = Number(context.query.id);
  const article = await ArticleAPI.article(id);
  return {
    props: article,
  };
}
