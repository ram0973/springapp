import useSWR from 'swr'
import ArticlesOnMain from "../components/articles/ArticlesOnMain";

const fetcher = (url: string, init?: RequestInit) => fetch(url, init).then((res: Response) => res.json())

export default function Index() {
  const {data, error} = useSWR('http://localhost:8080/api/articles/', fetcher)
  if (error) return <div>Failed to load</div>
  if (!data) return <div>Loading...</div>
  return (
    <ArticlesOnMain articlesPaged={data}/>
  )
}
