<template>
  <div id="app">
    <navigation-bar v-if="!isLogin" :items="navigationItems" />
    <div
      class="container window-container"
      :class="isLogin ? 'center-vertically center-horizontally' : ''"
    >
      <div class="display">
        <router-view id="view" />

        <stop-watch
          id="watch"
          v-if="!isLogin && $store.state.user.id != null"
        />
      </div>
    </div>
    <footer v-if="!isLogin">
      <p class="copyright text-center">
        Copyright &copy; {{ year }}. All Rights Reserved.
      </p>
    </footer>
  </div>
</template>

<script>
import NavigationBar from "@/components/Navigation.vue";
import StopWatch from "@/components/StopWatch";

export default {
  components: {
    NavigationBar,
    StopWatch,
  },
  data() {
    return {
      navigationItems: [
        { text: "Projects", component: "projects" },
        { text: "Time Logs", component: "times" },
        { text: "Profile", component: "profile" },
        { text: "About Us", component: "about-us" },
      ],
    };
  },
  computed: {
    isLogin() {
      return this.$route.name === "login" || this.$route.name === "register";
    },
    year() {
      return new Date().getFullYear();
    },
  },
};
</script>

<style scoped>
.window-container {
  margin-top: 115px;
}

footer {
  background-color: #363636;
  padding: 1rem;
  margin-top: 2rem;
}

footer p {
  margin: 0;
}

.copyright {
  color: #686868;
}
/* .display {
  display: flex;
  flex-direction: row;
  
} */
</style>
