import { getAccessToken } from '../api'

export const login = ({ commit }, data) => {
  console.log('####### login ' + data.username + ', ' + data.password)
  getAccessToken(data)
    .then((value) => {
      commit('SAVE_USER', value)
      console.log('## value : ' + value.access_token)
      return true
    }).catch((err) => {
      console.log(err)
    })
  return false
}
