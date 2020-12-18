<template>
  <body>
    <div class="table-wrapper">
      <div class="table-title">
              <div id="title">Current Projects</div>
              <div class="sort-buttons">
                <button
                  class="btn project-name"
                  v-on:click="$store.commit('SORT_PROJECTS_BY_NAME')"
                >
                  Name
                </button>
                <button
                  class="btn project-date"
                  v-on:click="$store.commit('SORT_PROJECTS_BY_DATE')"
                >
                  Date
                </button>
                <button
                  class="btn project-recent"
                  v-on:click="$store.commit('SORT_PROJECTS_BY_RECENT')"
                >
                  Recent
                </button>
          </div>

      </div>
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th></th>
            <th>Project Name</th>
            <th>End Date</th>
            <th>Edit</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="project in $store.state.projects" v-bind:key="project.id">
            <td>
              <span>
                <img
                  v-if="project.projectImg != ''"
                  :src="project.projectImg"
                />
                <img
                  v-if="project.projectImg === ''"
                  src="img\kronos-logo.png"
                />
              </span>
            </td>
            <td>
              <span>{{ project.projectName }}</span>
            </td>
            <td>
              <span>{{ project.endDate }}</span>
            </td>
            <td>
              <router-link
                :to="{ name: 'editProject', params: { id: project.projectID } }"
                ><font-awesome-icon icon="edit" id="edit"
              /></router-link>
            </td>
            <td>
              <a href="#" v-on:click="deleteProject(project.projectID)"
                ><font-awesome-icon icon="trash" id="trash"
              /></a>
            </td>
          </tr>
        </tbody>
      </table>
              </div>
  </body>
</template>


<script>
import projectService from "@/services/ProjectService.js";
export default {
  name: "project-list",
  data() {
    return {
      project: {
        projectName: "",
        projectDescription: "",
        projectImg: "",
        endDate: "",
        shared: false,
      },
    };
  },
  methods: {
    getProjects() {
      projectService.getAllProjects().then((response) => {
        this.$store.commit("SET_PROJECTS", response.data);
      });
    },
    deleteProject(projectID) {
      if (
        confirm(
          "Are you sure you want to delete project? This action cannot be undone."
        )
      ) {
        projectService
          .deleteProject(projectID)
          .then((response) => {
            {
              if (response.status === 200) {
                this.getProjects();
              }
            }
          })
          .catch((error) => {
            if (error.response) {
              return (
                "Failed to update project. Response was: " +
                error.response.data.message
              );
            } else if (error.request) {
              return "Failed to connect to server.";
            } else {
              return "Something went really wrong.";
            }
          });
      }
    },
  },
  created() {
    this.getProjects();
  },
};
</script>

<style scoped>
.table-wrapper {

  background: #fff;
  padding: 20px 30px 5px;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.7);
}

.table-title .btn {
  min-width: 50px;
  border-radius: 1px;
  border: none;
  padding: 6px 12px;
  font-size: 95%;
  outline: none !important;
  height: 30px;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.7);
}
.table-title {
  display: flex;
  flex-flow: row nowrap;
  justify-content: flex-end;
  border-bottom: 1px solid #a3e000;
  padding-bottom: 15px;
  background-color: #363636;
  margin: -20px -31px 10px;
  padding: 15px 30px;
  color: #fff;
}

table.table tr th,
table.table tr td {
  border-color: #e9e9e9;
  padding: 12px 15px;
  vertical-align: middle;
}
table.table tr th:first-child {
  width: 40px;
}
table.table tr th:last-child {
  width: 100px;
}
table.table-striped tbody tr:nth-last-of-type(odd) {
  background-color: #fcfcfc;
}

#edit {
  color: #3498db;
}

#trash {
  color: #c74513;
}

img {
  width: 50px;
  border-radius: 50%;
}

.project-name {
  background: #a3e000;
  font-weight: bold;
  color: #000;
  border: none;
}

.project-date {
  background: #e07700;
  font-weight: bold;
  color: #000;
  border: none;
}

.project-recent {
  background: #b300e0;
  font-weight: bold;
  color: #000;
  border: none;
}

.btn {
  border: none;
  padding: 20px;
  text-align: center;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
}

  .btn:hover {
    color: white;
  }

#title {
  font-size: 25px;
}

.sort-buttons {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  flex-grow: 1;
}



</style>





