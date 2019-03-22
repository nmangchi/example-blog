import localforage from 'localforage'
import axios from 'axios'
import { API_URL, AUTH_CLIENT } from './consts'

const redirectLogin = () => {
  window.location.href = '/login'
}
export const callToken = (data, grantType) => {
  data = Object.assign({grant_type: grantType}, data)
  let config = {
    url: '/oauth/token',
    method: 'post',
    baseURL: API_URL,
    auth: AUTH_CLIENT,
    params: data
  }
  return axios.request(config)
    .then((res) => {
      if (res.status === 200) {
        return localforage.setItem('user', res.data)
          .then((value) => {
            return value
          })
          .catch((err) => {
            return Promise.reject(err)
          })
      }
    })
    .catch((err) => {
      console.log('########### err : ' + err)
      if (err.response && err.response.status === 400) {
        return Promise.reject(err.response.data.error_description)
      } else {
        return Promise.reject(err)
      }
    })
}
export const callAPI = (url, method, data) => {
  console.log('url : ' + url)
  console.log('method : ' + method)
  console.log('data : ' + JSON.stringify(data))
  return localforage.getItem('user')
    .then((value) => {
      console.log('getItem value : ' + JSON.stringify(value))
      let token = value ? value.access_token : false
      console.log('token : ' + token)
      if (token) {
        let config = {
          url: url,
          method: method,
          baseURL: API_URL,
          headers: { 'Authorization': 'Bearer ' + token },
          data: data
        }
        return axios.request(config)
          .then((res) => {
            console.log('callAPI res : ' + JSON.stringify(res))
            console.log('callAPI res.status : ' + res.status)
            return res.data
          })
          .catch((err) => {
            console.log('callAPI err : ' + JSON.stringify(err))
            console.log('callAPI err.response.status : ' + err.response.status)
            if (err.response.status === 401) {
              console.log('refresh token : ' + value.refresh_token)
              let user = { refresh_token: value.refresh_token }
              callToken(user, 'refresh_token')
                .then((res) => {
                  console.log('refresh token res : ' + JSON.stringify(res))
                })
                .catch((err) => {
                  console.log('refresh token err : ' + JSON.stringify(err))
                  if (err.response.status === 401) {
                    redirectLogin()
                  }
                })
            }
          })
      } else {
        console.log('callAPI need to login')
        redirectLogin()
      }
    })
    .catch((err) => {
      console.log('err : ' + err)
    })
}
