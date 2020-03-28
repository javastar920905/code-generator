<template>
  <div id="app">
    <!--引入 Layout布局-->
     <layout></layout>
  </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import Layout from './pages/Layout'

export default {
  name: 'App',
  components: {
    Layout
  }
}
</script>

<style>
#app {
  margin: 0px;
  padding: 0px;
  height: 100%;
}
</style>
