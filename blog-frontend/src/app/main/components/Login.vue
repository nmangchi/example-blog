<template>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              <v-alert :value="!success" type="error">{{error_msg}}</v-alert>
              <v-form @submit.prevent="processLogin">
              <v-card-text>
                  <v-text-field v-model="username" prepend-icon="person" name="username" label="Username" type="text"></v-text-field>
                  <v-text-field v-model="password" prepend-icon="lock" name="password" label="Password" type="password"></v-text-field>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" type="submit">Login</v-btn>
              </v-card-actions>
              </v-form>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'login',
  data: () => ({
    username: '',
    password: '',
    success: true,
    error_msg: 'login failed'
  }),
  props: {
    source: String
  },
  methods: {
    ...mapActions([
      'login'
    ]),
    processLogin () {
      let user = { username: this.username, password: this.password }
      this.login(user)
        .then((value) => {
          this.success = value
          if (value) {
            this.$router.push({ name: 'home' })
          }
        })
        .catch((err) => {
          this.error_msg = err.toString()
          this.success = false
        })
    }
  }
}
</script>

<style scoped>

</style>
