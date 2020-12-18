<template>
  <div class="user-profile">
    <div class="row">
      <div class="col-md-3">
        <img v-bind:src="loggedInUser.avatar" :alt="`Profile Photo of ${loggedInUserFullName}`" />
      </div>
      <div class="col-md-9">
        <h1 class="mb-4">Profile</h1>
        <form>
          <div class="form-group">
            <label>First Name</label><br>
            <input v-if="isEditable" v-model="loggedInUser.firstName" class="form-control">
            <p v-else class="form-control">{{ loggedInUser.firstName }}</p>
          </div>
          <div class="form-group">
            <label>Last Name</label><br>
            <input v-if="isEditable" v-model="loggedInUser.lastName" class="form-control">
            <p v-else class="form-control">{{ loggedInUser.lastName }}</p>
          </div>
          <div class="form-group">
            <label>Email</label><br>
            <input v-if="isEditable" v-model="loggedInUser.email" class="form-control">
            <p v-else class="form-control">{{ loggedInUser.email }}</p>
          </div>
          <div v-if="!isEditable">
            <label>Manager</label><br>
            <p class="form-control">{{ loggedInUser.managerFirstName }} {{loggedInUser.managerLastName }}</p>
          </div>
          <div v-if="isEditable">
            <label>Change Your Picture</label><br>
            <input v-if="isEditable" v-model="loggedInUser.avatar" class="form-control">
          </div>
          <div v-if="isEditable" class="mt-3">
            <label>Select Manager</label><br>
            <select v-model="selectedManagerUserId" class="form-control">
              <option v-for="user in allOtherUsers" :key="user.id" :value="user.id">
                {{ user.firstName }} {{ user.lastName }}
              </option>
            </select>
          </div>
        </form>

        <div class="row">
          <div class="col-6">
            <button v-if="isEditable" class="btn btn-primary btn-block" @click="updateProfile()">
              <font-awesome-icon icon="check" /> Save
            </button>
            <button v-else class="btn btn-primary btn-block" @click="isEditable = true">
              <font-awesome-icon icon="edit" /> Edit
            </button>
          </div>
          <div class="col-6">
            <button v-if="isEditable" class="btn btn-secondary btn-block" @click="onCancel()">
              <font-awesome-icon icon="window-close" /> Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserServices from "../services/UserServices.js";
import { handleServiceError } from '@/js/utility.methods.js';

export default {
  data() {
    return {
      isEditable: false,
      users: [],
      loggedInUser: { ...this.$store.state.user }, // Logged in user data already stored in state. Assigning to local variable to modify
      selectedManagerUserId: undefined,
    }
  },
  methods: {
    updateProfile() {
      // Don't need to make a copy of it. User is already stored in local component data.
      // this.user is automatically binded when user types in form
      this.loggedInUser.managerID = this.selectedManagerUserId // Assigning manager to user by unique ID
      this.loggedInUser.managerFirstName = this.findFirstNameByID(this.selectedManagerUserId)[0];
      this.loggedInUser.managerLastName = this.findLastNameByID(this.selectedManagerUserId)[0];
      delete this.loggedInUser.authorities;

      UserServices.updateProfile(this.loggedInUser)
      .then((response) => {
        if (response.status === 200) {
          this.isEditable = false
          // Commit new loggedin user into Vuex store - prevents from have to make a GET service call to update data
          this.$store.commit('SET_USER', this.loggedInUser)
        }
      })
      .catch((error) => { handleServiceError(error) });
    },
    findFirstNameByID(id) {
      return this.users.filter(user => user.id === id).map(user => user.firstName);
    },
    findLastNameByID(id) {
      return this.users.filter(user => user.id === id).map(user => user.lastName);
    },
    onCancel() {
      this.isEditable = false
      this.loggedInUser = this.$store.state.user
    }
  },
  computed: {
    allOtherUsers() {
      // Get all users EXCEPT logged in user
      return this.users.filter((user) => {
        return user.id != this.$store.state.user.id // single '=' bc comparing to string
      })
    },
    loggedInUserFullName() {
      return `${this.loggedInUser.firstName} ${this.loggedInUser.lastName}`
    },
  },
  mounted() {
    // Get all users on component
    UserServices.getAllUsers().then((response) => {
      this.users = response.data;
    });
  },
}
</script>

<style scoped>
  .user-profile {
    width: 100%;
    margin: 0 auto;
  }

  form {
    margin-bottom: 1rem;
  }

  label {
    padding: 0;
    font-weight: bold;
  }

  img {
    width: 100% !important;
    height: auto;
    border-radius: 5px;
    max-width: 250px;
    margin: 0 auto 3rem auto;
    display: block;
  }
</style>