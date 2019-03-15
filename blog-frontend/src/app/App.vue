<template>
  <v-app>
    <navigation></navigation>
    <router-view/>
  </v-app>
</template>

<script>
import { Navigation } from './main/components'
export default {
  name: 'App',
  components: {
    Navigation
  }
}
</script>

<style>

</style>
