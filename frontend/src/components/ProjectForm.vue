<template>
  <div class="mb-4">
    <single-card>
      <form v-on:submit="submitForm" class="projectForm">
        <div class="form-group">
          <label for="name">Name:</label>
          <input
            id="name"
            type="text"
            class="form-control"
            v-model="project.projectName"
            autocomplete="off"
          />
        </div>
        <div class="form-group">
          <label for="description">Description:</label>
          <textarea
            id="description"
            class="form-control"
            v-model="project.projectDescription"
          ></textarea>
        </div>
        <div class="form-group">
          <label for="image">Image (Optional):</label>
          <input
            id="image"
            type="text"
            class="form-control"
            v-model="project.projectImg"
            autocomplete="off"
          />
        </div>
        <div class="form-group">
          <label for="end-date">Completion Date:</label>
          <input
            id="end-date"
            type="date"
            class="form-control"
            v-model="project.endDate"
          />
        </div>
        <div class="form-group">
          <button class="btn btn-secondary" type="submit">Submit</button>
        </div>
      </form>
    </single-card>
  </div>
</template>

<script>
import projectService from "../services/ProjectService";
import SingleCard from "../components/SingleCard.vue";

export default {
  name: "project-form",
  components: {
    SingleCard,
  },
  data() {
    return {
      project: {
        projectName: "",
        projectDescription: "",
        projectImg: "",
        ongoing: false,
        endDate: null,
        shared: false,
      },
    };
  },
  methods: {
    getValidationErrorMessage() {
      if (this.project.projectName === "") {
        return "Please create a name for your project.";
      } else if (this.project.projectDescription === "") {
        return "Please provide a description of your project.";
      }
    },
    submitForm() {
      let errorMessage = this.getValidationErrorMessage();
      if (errorMessage != null) {
        return alert(errorMessage);
      } else {
        projectService
          .createProject(this.project)
          .then((response) => {
            if (response.status === 201) {
              alert("New project was created successfully");
            }
          })
          .catch((error) => {
            {
              if (error.response) {
                this.errorMsg =
                  "Failed to create project. Response was: " +
                  error.response.data;
              } else if (error.request) {
                this.errorMsg =
                  "Server did not respond. Could not create project.";
              } else {
                this.errorMsg = "Something went wrong. Could not send request.";
              }
            }
          });
      }
    },
  },
};
</script>

<style scoped>
img {
  position: relative;
  width: 50px;
  height: 50px;
  overflow: hidden;
  border-radius: 50%;
}
.project-title {
    border-bottom: 1px solid #A3E000;
    padding-bottom: 15px;
    background-color: #363636;
    margin: -20px -31px 10px;
    padding: 15px 30px;
    color: #fff;
    border: 1px solid black;
}

.table-wrapper {
    background: #fff;
    padding: 20px 30px 5px;
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.7);
}

#title {
  font-size: 25px;
}
</style>