<template>
  <div>
    <v-toolbar color="pink" dark>
      <v-toolbar-side-icon></v-toolbar-side-icon>
      <v-toolbar-title>My Post</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>search</v-icon>
      </v-btn>
      <router-link :to="{ name: 'home' }">
        <v-btn fab small><v-icon>home</v-icon></v-btn>
      </router-link>
      <router-link :to="{ name: 'articleList' }">
        <v-btn fab small><v-icon>list</v-icon></v-btn>
      </router-link>
      <router-link :to="{ name: 'articleWrite' }">
        <v-btn fab small><v-icon>edit</v-icon></v-btn>
      </router-link>
      <router-link :to="{ name: 'login' }">
        <v-btn fab small><v-icon>account_circle</v-icon></v-btn>
      </router-link>
    </v-toolbar>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'

export default {
  name: 'navigation',
  computed: {
    ...mapGetters([
    ]),
    ...mapState({
      'user': state => state.main.user
    })
  },
  mounted () {
  },
  methods: {
  }
}
</script>

<style scoped>

</style>
