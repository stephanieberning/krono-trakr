<template>
  <div id="register" class="text-center">
    <router-link :to="{ name: 'splash' }"><img src="img/kronos-logo.png" alt="Kronos Logo"></router-link>

    <form class="form-register card" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Sign Up</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="input-group flex-nowrap">
        <label for="firstName" class="sr-only">First Name</label>
        <span class="input-group-text" id="addon-wrapping">
          <font-awesome-icon icon="id-card" />
        </span>
        <input
          type="text"
          id="firstName"
          class="form-control"
          placeholder="First Name"
          v-model="user.firstName"
          required
          autofocus
        />
      </div>
      <div class="input-group flex-nowrap">
        <label for="lastName" class="sr-only">Last Name</label>
        <span class="input-group-text" id="addon-wrapping"
          ><font-awesome-icon icon="id-card" />
        </span>
        <input
          type="text"
          id="lastName"
          class="form-control"
          placeholder="Last Name"
          v-model="user.lastName"
          required
          autofocus
        />
      </div>
      <div class="input-group flex-nowrap">
        <label for="email" class="sr-only">Email Address</label>
        <span class="input-group-text" id="addon-wrapping"
          ><font-awesome-icon icon="at" />
        </span>
        <input
          type="text"
          id="email"
          class="form-control"
          placeholder="Email Address"
          v-model="user.email"
          required
          autofocus
        />
      </div>
      <div class="input-group flex-nowrap">
        <label for="avatar" class="sr-only">Avatar</label>
        <span class="input-group-text" id="addon-wrapping"
          ><font-awesome-icon icon="portrait" />
        </span>
        <input
          type="text"
          id="avatar"
          class="form-control"
          placeholder="Image url"
          v-model="user.avatar"
          autofocus
          required
        />
      </div>
      <div class="input-group flex-nowrap">
        <label for="username" class="sr-only">Username</label>
        <span class="input-group-text" id="addon-wrapping"
          ><font-awesome-icon icon="user-secret" />
        </span>
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
      </div>
      <div class="input-group flex-nowrap">
        <label for="password" class="sr-only">Password</label>
        <span class="input-group-text" id="addon-wrapping"
          ><font-awesome-icon icon="lock" />
        </span>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
      </div>
      <div class="input-group flex-nowrap">
        <span class="input-group-text" id="addon-wrapping"
          ><font-awesome-icon icon="lock" />
        </span>
        <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        />
      </div>
      <router-link :to="{ name: 'login' }" class="mt-2 mb-2">Have an account?</router-link>
      <button class="btn btn-lg btn-primary mt-2" type="submit">Create Account</button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Username Already Exists";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
#register {
  max-width: 600px;
  width: 100%;
  margin: 0 auto;
}

.input-group {
  margin-bottom: 1rem;
}

label {
  display: flex;
  padding: 20px;
}

.button {
  display: flex;
  justify-content: center;
  padding: 10px 0px;
}

img {
  width: 100px !important;
  margin-bottom: 1rem;
}
</style>

