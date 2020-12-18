// @ts-nocheck

import axios from 'axios';

export default {

  login(user) {
    return axios.post('/token', user)
  },

  register(user) {
    return axios.post('/users', user)
  }

}
