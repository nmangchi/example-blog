import { postArticle, fetchArticles } from '../api'

export const saveArticle = ({ state, commit, rootState }, data) => {
  console.log('data : ' + JSON.stringify(data))
  console.log('state : ' + state)
  console.log('commit : ' + commit)
  console.log('rootState : ' + rootState)
  console.log('rootState.main : ' + rootState.main)
  console.log('rootState.main.user : ' + JSON.stringify(rootState.main.user))
  // postArticle(data, rootState.main.user.access_token)
  return postArticle(data)
}
export const loadArticles = () => {
  return fetchArticles()
}
