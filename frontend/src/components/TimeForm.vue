<template>
  <div class="mb-4">
    <single-card>
      <form class="timeForm">
        <div class="form-group">
          <label for="project name">Project Name:</label>
          <select
            name="projects"
            id="project name"
            v-model="timesheet.projectID"
            class="form-control"
          >
            <option
              v-bind:value="project.projectID"
              v-for="project in this.$store.state.projects"
              v-bind:key="project.projectName"
            >
              {{ project.projectName }}
            </option>
          </select>
          <button class="btn btn-secondary mt-2" v-on:click.prevent="clear()">Clear</button>
        </div>
        <hr>
        <div v-if="!runReports" class="form-group">
          <label for="description">Description:</label>
          <input
            type="text"
            maxlength="50"
            id="description"
            class="form-control"
            v-model="timesheet.description"
          />
        </div>
        <div class="form-group">
          <label for="beginning time">Beginning Date/Time:</label>
          <input
            id="beginning time"
            class="form-control"
            type="datetime-local"
            v-model="timesheet.beginningTime"
          />
        </div>
        <div class="form-group">
          <label for="description">Ending Date/Time:</label>
          <input
            id="ending time"
            class="form-control"
            type="datetime-local"
            v-model="timesheet.endingTime"
          />
        </div>
        <div class="form-group">
          <button
            v-on:click.prevent="submitForm()"
            class="btn btn-primary btn-lg mt-4"
            v-if="!runReports"
          >
            Create Log
          </button>
          <button
            v-on:click.prevent="generateReport()"
            class="btn btn-primary btn-lg mt-4"
            v-if="runReports"
          >
            Run Report
          </button>
        </div>
      </form>
    </single-card>
  </div>
</template>

<script>
import timesheetService from "../services/TimesheetService";
import SingleCard from '@/components/SingleCard'
export default {
  name: "time-form",
  components: {
    SingleCard
  },
  props: ["runReports"],
  data() {
    return {
      timesheet: {
        timeID: "",
        projectID: "",
        userID: "",
        description: "",
        beginningTime: "",
        endingTime: "",
      },
    };
  },
  methods: {
    getValidationErrorMessage() {
      if (this.timesheet.projectID === "") {
        return "Please select a project.";
      } else if (this.timesheet.description === "") {
        return "Please provide a description of your activity.";
      } else if (this.timesheet.beginningTime > this.timesheet.endingTime) {
        return "Beginning date/time must occur before the end.";
      } else if (this.timesheet.beginningTime === "") {
        return "Beginning date/time cannot be left blank.";
      } else if (this.timesheet.endingTime === "") {
        return "Ending date/time cannot be left blank.";
      }
    },
    submitForm() {
      let errorMessage = this.getValidationErrorMessage();
      if (errorMessage != null) {
        return alert(errorMessage);
      } else {
        timesheetService
          .createTimesheet(this.timesheet)
          .then((response) => {
            if (response.status === 201) {
              alert("New timesheet was created successfully.");
              this.refreshTimesheets();
              this.resetForm();
            }
          })
          .catch((error) => {
            {
              if (error.response) {
                this.errorMsg =
                  "Failed to create timesheet. Response was: " +
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
    refreshTimesheets() {
      timesheetService.getAllTimesheets().then((response) => {
        this.timesheets = response.data;
        this.$store.commit("SET_TIMESHEETS", response.data);
      });
    },
    resetForm() {
      this.timesheet = {
        timeID: "",
        projectID: "",
        userID: "",
        description: "",
        beginningTime: "",
        endingTime: "",
      };
      this.$emit("reset-form", null);
    },
    clear() {
      this.timesheet.projectID = "";
    },
    generateReport() {
      let report = this.$store.state.timesheets;
      if (this.timesheet.projectID != null && this.timesheet.projectID != "") {
        report = report.filter(
          (elem) => elem.projectID == this.timesheet.projectID
        );
      }
      if (
        this.timesheet.beginningTime != null &&
        this.timesheet.beginningTime != ""
      ) {
        report = report.filter(
          (elem) => elem.beginningTime >= this.timesheet.beginningTime
        );
      }
      if (
        this.timesheet.endingTime != null &&
        this.timesheet.endingTime != ""
      ) {
        report = report.filter(
          (elem) => elem.endingTime <= this.timesheet.endingTime
        );
      }
      this.$store.commit("GENERATE_REPORT", report);
    },
  },
};
</script>

<style>

.time-title {
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

.option:hover{
  background: #A3E000;
}
</style>