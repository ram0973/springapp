export interface Article {
  id: number;
  title: string,
  slug: string,
  excerpt: string,
  image: string,
  createdAt: string,
}

export interface ArticlesPaged {
  articles: Article[],
  currentPage: number,
  totalItems: number,
  totalPages: number,
}

export interface LoginFormData extends FormData {
  email: string,
  password: string,
}