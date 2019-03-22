import { callToken } from '../../api-utils'

export const getToken = (user) => {
  return callToken(user, 'password')
    .then((value) => {
      return value
    })
    .catch((err) => {
      return Promise.reject(err)
    })
}
