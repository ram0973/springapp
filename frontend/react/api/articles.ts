import {SERVER_BASE_URL} from "../utils/constants";
import {Article, ArticlesPaged} from "./types";

const ArticleAPI = {
  articles: async (page: number): Promise<ArticlesPaged> => {
    try {
      const res = await fetch(`${SERVER_BASE_URL}/articles?page=${page}`, {});
      return await res.json();
    } catch (error: any) {
      return error.response;
    }
  },

  article: async (id: number): Promise<Article> => {
    try {
      const res = await fetch(`${SERVER_BASE_URL}/articles/${id}`, {});
      return await res.json();
    } catch (error: any) {
      return error.response;
    }
  },
};

export default ArticleAPI;
