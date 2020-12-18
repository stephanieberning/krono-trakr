<template>
<div>
  <router-link :to="{ name: 'splash' }"><img src="img/kronos-logo.png" alt="Kronos Logo"></router-link>
  <h1 class="text-center">KronoTrakr</h1>
    <single-card id="login" class="text-center mt-4">
      <form class="form-signin" @submit.prevent="login">
        <h2 class="h3 mb-3 font-weight-normal">Sign In</h2>
        <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>
        <div
          class="alert alert-success"
          role="alert"
          v-if="this.$route.query.registration"
        >
          Thank you for registering, please sign in.
        </div>
        <div class="input-group flex-nowrap">
          <span class="input-group-text" id="addon-wrapping"><font-awesome-icon icon="user-secret"/> </span>
          <input
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            aria-label="Username"
            aria-describedby="addon-wrapping"
            v-model="user.username"
          required
          autofocus
          />
        </div>
        <div class="input-group flex-nowrap">
          <span class="input-group-text" id="addon-wrapping"><font-awesome-icon icon="lock"/> </span>
          <input
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            aria-label="Password"
            aria-describedby="addon-wrapping"
            v-model="user.password"
          required
          />
        </div>
        <router-link :to="{ name: 'register' }">Need an account?</router-link>
        <div class="button">
        <button class="btn btn-primary btn-md btn-block" type="submit">SIGN IN</button>
        </div>
      </form>
    </single-card>
  </div>
</template>

<script>
import authService from "../services/AuthService.js";
import userServices from "../services/UserServices.js";
import SingleCard from '@/components/SingleCard.vue'

export default {
  name: "login",
  components: {
    SingleCard
  },
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
 methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.getMyEmployees();
            this.$router.push("/projects");
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
    getMyEmployees() {
      userServices.getEmployees().then(response => {
         this.$store.commit("SET_EMPLOYEES", response.data);
      })
    }
  },
};
</script>

<style scoped>
#login {
  margin: 0 auto;
  min-width: 450px;
}

h1 {
  font-weight: bold;
  font-style: italic;
}

.input-group {
  margin-bottom: 1rem;
}

.button{
  display: flex;
  justify-content: center;
  padding: 10px 0px;
}

img {
  display: block;
  margin: 0 auto;
  width: 100px;
}
</style>
