import http from 'axios';

export default {
  updateProfile(user) {
    return http.put(`/users/profile/${user.id}`, user);
  },
  getUser(user) {
    return http.get('/users/profile/', user);
  },
  getAllUsers() {
    return http.get(`/users`);
  },
  getEmployees() {
    return http.get('/employees');
  }

}