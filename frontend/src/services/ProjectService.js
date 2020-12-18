// @ts-nocheck

import http from 'axios';



export default {

  updateProject(project) {
    return http.put(`/projects/${project.projectID}`, project);
  },

  deleteProject(projectID) {
    return http.delete(`/projects/${projectID}`);
  },

  createProject(project){
    return http.post('/projects', project);
  },

  getAllProjects() {
    return http.get('/projects');
  },

  getProjectByID(projectID) {
    return http.get(`/projects/${projectID}`);
  }

}