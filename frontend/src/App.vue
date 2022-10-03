<template>
  <nav-bar/>
  <router-view/>
  <footer-bar/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  text-decoration: none;
}
nav a:hover {
  font-weight: bold;
  color: #FF73A5;
  text-decoration: none;
}
</style>
<script>
import NavBar from '@/components/NavBar.vue';
import FooterBar from '@/components/FooterBar.vue';

export default {
  name: 'App',
  components: {
    NavBar,
    FooterBar
  }
}

</script>

