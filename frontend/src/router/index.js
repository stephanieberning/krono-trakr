// @ts-nocheck

import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Project from '../views/Project.vue'
import EditProject from '../views/EditProject.vue'
import Profile from '../views/Profile.vue'
import Timesheet from '../views/Timesheet.vue'
import AboutUs from '../views/AboutUs.vue'
import Splash from '../views/Splash.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/logout',
      name: 'logout',
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/projects/:id',
      name: 'editProject',
      component: EditProject,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/projects',
      name: 'project',
      component: Project,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/times',
      name: 'timesheet',
      component: Timesheet,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/times/:id',
      name: 'deletetimesheet',
      component: Timesheet,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/about-us',
      name: 'aboutus',
      component: AboutUs,
      meta: {
        requiresAuth: false
      }
    },

    {
      path:'/',
      name:'splash',
      component:Splash,
      meta: {
        requiresAuth: false
      }
    }
   
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next('/login');
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
