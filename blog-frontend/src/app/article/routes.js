import * as components from './components'

export default [
  {
    path: '/articles',
    name: 'articleList',
    component: components.ArticleList
  },
  {
    path: '/articles/{id}',
    name: 'articleDetail',
    component: components.ArticleDetail
  },
  {
    path: '/articles/write',
    name: 'articleWrite',
    component: components.ArticleDetail
  }
]
