import { getToken } from '../api'

export const login = ({ commit }, data) => {
  return getToken(data)
    .then((token) => {
      commit('SAVE_TOKEN', token)
      console.log('## token : ' + JSON.stringify(token))
      return true
    }).catch((err) => {
      console.log('get token error : ' + err)
      return Promise.reject(err)
    })
}
