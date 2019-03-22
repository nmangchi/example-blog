import { callAPI } from '../../api-utils'

export const postArticle = (article) => {
  console.log('article : ' + JSON.stringify(article))
  return callAPI('/api/articles', 'post', article)
    .then((value) => {
      console.log('saved value : ' + JSON.stringify(value))
      return value
    })
    .catch((err) => {
      console.log('post article error : ' + err)
    })
}
export const fetchArticles = () => {
  let data = {}
  return callAPI('/api/articles', 'get', data)
    .then((articles) => {
      console.log('articles : ' + JSON.stringify(articles))
      return articles
    })
    .catch((err) => {
      console.log('fetch article error : ' + err)
    })
}
