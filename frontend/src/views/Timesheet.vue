<template>
  <div>
    <button v-on:click="showCreateTime()" class="btn btn-primary mb-3 mr-2">Create Time</button>
    <button v-on:click="showReports()" class="btn btn-primary mb-3">Run Reports</button>

    <time-form v-bind:runReports="runReports" v-if="showForm" v-on:reset-form="runReports = undefined"/>
    <run-reports v-if="runReports === true"/>

    <time-list v-if="!runReports"/>
  </div>
</template>

<script>
import TimeForm from "../components/TimeForm.vue";
import TimeList from "../components/TimeList.vue";
import projectService from "@/services/ProjectService.js";
import RunReports from "../components/RunReports.vue";

export default {
  components: { TimeList, TimeForm, RunReports },
  data() {
    return{
      runReports: undefined,
      showForm: false
    }
  },
  methods: {
    showCreateTime() {
      if(this.runReports == false) {
        this.showForm = !this.showForm
      } else {
        this.showForm = true;
      }
      this.runReports = false;
    },
    showReports() {
      this.runReports = true;
      this.showForm = true;
    },
    getProjects() {
      projectService.getAllProjects().then((response) => {
        this.projects = response.data;
        console.log(this.projects);
        this.$store.commit("SET_PROJECTS", response.data);
      });
    },
  },
  created() {
    this.getProjects();
  },
};
</script>

<style>
.run-btn {
  background: #b300e0;
  border-radius: 4px;
  border: none;
  font-weight: bold;
  height: 36px;
  font-size: 16px;
  padding: 6px 12px;
  margin: 0px;
}
.run-btn:hover{
  background: #c614f3;
}
</style>