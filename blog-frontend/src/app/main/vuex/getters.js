export default {
  accessToken: (state) => () => {
    return state.user && state.user.access_token ? state.user.access_token : false
  }
}
