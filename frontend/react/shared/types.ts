export type UriString = string
export type UniqueString = string
export type EntityId = number | UniqueString

export type Category = "Technology" | "Science" | "Arts"
export type DateIsoString = string

export type Article = {
  id: EntityId
  dateCreated: DateIsoString
  title: string
  excerpt: string
  content: string
  image: UriString
}
