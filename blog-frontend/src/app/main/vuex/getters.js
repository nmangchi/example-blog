export default {
  token: (state) => () => {
    return state.token && state.token.access_token ? state.token.access_token : false
  }
}
