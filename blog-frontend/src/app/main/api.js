import localforage from 'localforage'
import axios from 'axios'
import { API_URL, AUTH_CLIENT } from '../../consts'

export const getAccessToken = (user) => {
  user = Object.assign({grant_type: 'password'}, user)
  let config = {
    url: '/oauth/token',
    method: 'post',
    baseURL: API_URL,
    auth: AUTH_CLIENT,
    params: user
  }
  return axios.request(config)
    .then((res) => {
      console.log(res)
      if (res.status === 200) {
        console.log(res.data.access_token)
        return localforage.setItem('user', res.data)
          .then((value) => {
            return value
          })
          .catch((err) => {
            console.log('setItem error ' + err)
          })
      }
    })
    .catch((err) => {
      console.log(err)
    })
}
