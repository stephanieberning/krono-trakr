import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    projects: [],
    activeProject: {
      projectID: 0,
      projectName: '',
      projectDescription: '',
      projectImg: '',
      endDate: '',
      mostRecent: '',
    },
    timesheets: [],
       timesheet:  {
            timeID: '',
            projectID: '',
            projectName: '',
            userID: '',
            description: '',
            beginningTime: '',
            endingTime: ''
        },
    report: [],
    token: currentToken || '',
    user: currentUser || {},
    employees : []
  },

  mutations: {
    SET_EMPLOYEES(state, data) {
      state.employees = data;
    },
    SET_PROJECTS(state, data) {
      state.projects = data;
    },
    SET_TIMESHEETS(state, data) {
      state.timesheets = data;
    },
    SORT_PROJECTS_BY_NAME(state){
      state.projects = state.projects.sort((project1, project2) => {
        return project1.projectName.toLowerCase().localeCompare(project2.projectName.toLowerCase())
      });
    },
    SORT_PROJECTS_BY_DATE(state){
      state.projects.sort((project1, project2) => {
        if(project1.endDate > project2.endDate) {
          return -1;
        } else if(project1.endDate < project2.endDate){
          return 1;
        } else {
          return 0;
        }
      });
    },
    SORT_PROJECTS_BY_RECENT(state){
      state.projects.sort((project1, project2) => {
        if(project2.mostRecent == null) {
          if(project1.mostRecent == null) {
            return 0;
          } else {
            return -1;
          }
        } else {
          if(project1.mostRecent == null) {
            return 1;
          }
        }
        if(project1.mostRecent > project2.mostRecent) {
          return -1;
        } else if(project1.mostRecent < project2.mostRecent){
          return 1;
        } else {
          return 0;
        }
      });
    },
    GENERATE_REPORT (state, data) {
      state.report = data;
    },
    SET_ACTIVE_PROJECT(state, data) {
      state.activeProject = data;
    },
    SET_ACTIVE_TIMESHEET(state, data) {
      state.timesheet = data;
    },
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
      state.projects = [];
      state.employees = [];
    },
  }
})
