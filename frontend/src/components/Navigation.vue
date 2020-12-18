<template>
  <nav>
    <div class="container nav-container">
      <router-link :to="{ name: 'splash' }"><img
        id="icon"
        class="left nav-logo"
        src="img\Kronotrakr.png"
        alt="Kronos Logo"
        
      /></router-link>
      <ul class="list-unstyled nav-item" v-if="$store.state.token !== ''">
        <li v-for="item in items" :key="item.component" class="d-inline">
          <router-link :to="item.component">{{ item.text }}</router-link>
        </li>
      </ul>
      <div
        class="nav-item"
        id="user"
        v-if="
          this.$store.state.user.id != '' && this.$store.state.user.id != null
        "
      >
        Hello, {{ this.$store.state.user.firstName }}!
      </div>
      <div class="d-inline nav-item">
        <router-link :to="{ name: 'logout' }" class="btn btn-primary btn-md" v-if="
          this.$store.state.user.id != '' && this.$store.state.user.id != null
        "
          >LOGOUT</router-link
        >
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  props: {
    items: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      showDropdown: false
    }
  }
};
</script>

<style scoped>
nav {
  background-color: #363636;
  padding: 1rem;
  position: fixed;
  width: 100%;
  top: 0;
  z-index: 99999;
}

nav ul li {
  padding: 0 1rem;
}

nav li a:not(.btn) {
  color: #666;
  transition: all 0.25s ease;
}

nav li a:not(.btn):hover {
  /* every anchor tag (link) that does NOT have class of .btn */
  color: #a3e000;
  transition: all 0.25s ease;
  text-decoration: none;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0px;
  margin-right: 0px;
}

.nav-container {
  height: 50px;
  justify-content: flex-end;
}

.nav-logo {
  position: fixed;
  left: 16px;
  top: 16px;
}

.nav-item {
  margin-left: 24px;
}

.list-unstyled {
  align-self: flex-end;
}

#icon {
  height: 50px;
  width: 300px;
}
#user {
  color: white;
  justify-items: flex-end;
}

li img {
  width: 50px;
  border-radius: 50%;
  margin-right: 1rem;
}

</style>