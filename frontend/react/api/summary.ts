import fetch from "node-fetch";
import axios from "axios"
import { Article } from "../shared/types";
import { config } from "./config";

export async function fetchArticles(): Promise<Article[]> {
  return await fetch(`${config.baseUrl}/articles`)
    .then(response => response.json()).then(data => data.articles);
}

const axiosInstance = axios.create({
  baseURL: `${config.baseUrl}`,
  timeout: 5000,
  headers: {
    Authorization: localStorage.getItem('access_token')
        ? 'Bearer ' + localStorage.getItem('access_token')
        : null,
        "Content-Type": "application/json",
        accept: "application/json",
  },
});

