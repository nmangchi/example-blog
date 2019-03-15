<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs10 md10 offset-xs1 offset-md1>
        <v-text-field label="Title" name="title"></v-text-field>
      </v-flex>
      <v-flex xs10 md10 offset-xs1 offset-md1>
        <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor>
      </v-flex>
      <v-flex xs10 md10 offset-xs1 offset-md1 text-xs-center>
        <v-btn color="primary" @click="processSave">Save</v-btn>
        <v-btn @click="cancel">Cancel</v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'

export default {
  name: 'article-detail',
  data: () => ({
    editor: ClassicEditor,
    contents: '',
    editorConfig: {
    }
  }),
  computed: {
    ...mapGetters([
      'accessToken'
    ]),
    ...mapState({
    })
  },
  methods: {
    processSave () {
      console.log('process save')
      console.log(this.contents)
    },
    cancel () {
      this.$router.push({ name: 'articleList' })
    }
  }
}
</script>

<style scoped>

</style>
