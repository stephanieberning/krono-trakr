<template>
  <div>
    <single-card
      v-if="!loadingTimesheet"
      class="stop-watch"
      v-bind:class="{ minimized }"
    >
      <h3 id="title-time" v-on:click="minimized = !minimized" class="mb-3">
        <template v-if="showElapsedTimeInHeader">Time Tracker</template>
        <template v-else>{{ formattedElapsedTime }}</template>
      </h3>
      <!-- ACTIVE TIME LOG DOESN'T EXIST -->
      <div v-if="$store.state.timesheet == null">
        <label>Choose project:</label>
        <select
          class="mb-3 form-control"
          name="projects"
          id="project name"
          v-model="selectedProject"
          v-if="$store.state.timesheet == null"
        >
          <option
            v-bind:value="project.projectID"
            v-for="project in this.$store.state.projects"
            v-bind:key="project.projectName"
          >
            {{ project.projectName }}
          </option>
        </select>
        <div>
          <button
            class="btn btn-primary btn-lg btn-block"
            :disabled="selectedProject == null"
            v-on:click="start"
          >
            Start Timer
          </button>
        </div>
      </div>

      <!-- ACTIVE TIME LOG EXISTS -->
      <div class="footer-row" v-if="$store.state.timesheet != null">
        <p>Project: {{ $store.state.timesheet.projectName }}</p>
        <div>
          <label>Description</label>
          <input
            class="form-control mb-3"
            maxlength="50"
            type="text"
            v-model="description"
            placeholder="A brief description of your work"
          />
        </div>
        <p>{{ formattedElapsedTime }}</p>
        <div>
          <button
            class="btn btn-danger btn-lg btn-block"
            v-on:click="stop"
            v-if="$store.state.timesheet != null"
          >
            Stop Timer
          </button>
        </div>
      </div>
    </single-card>
  </div>
</template>

<script>
import timesheetService from "@/services/TimesheetService.js";
import ProjectService from "../services/ProjectService";
import SingleCard from "@/components/SingleCard.vue";

export default {
  name: "StopWatch",
  components: {
    SingleCard,
  },
  data() {
    return {
      elapsedTime: 0,
      timer: undefined,
      selectedProject: null,
      loadingTimesheet: false,
      description: "",
      minimized: true,
    };
  },
  computed: {
    formattedElapsedTime() {
      const date = new Date(null);
      date.setSeconds(this.elapsedTime / 1000);
      const utc = date.toUTCString();
      return utc.substr(utc.indexOf(":") - 2, 8);
    },
    seconds() {
      return Math.floor((this.elapsedTime / 1000) % 60);
    },
    minutes() {
      return Math.floor((this.elapsedTime / 1000 / 60) % 60);
    },
    hours() {
      return Math.floor((this.elapsedTime / (1000 * 60 * 60)) % 24);
    },
    days() {
      return Math.floor(this.elapsedTime / (1000 * 60 * 60 * 24));
    },
    showElapsedTimeInHeader() {
      // Only show time in stop watch header if time is running and minimized
      return (
        (this.elapsedTime == 0 && this.minimized === true) ||
        (this.minimized === false && this.formattedElapsedTime)
      );
    },
  },
  filters: {
    formatTime(value) {
      if (value < 10) {
        return "0" + value;
      }
      return value;
    },
  },
  methods: {
    start() {
      this.description = "";
      timesheetService.createActiveTimesheet(this.selectedProject).then(
        () => {
          this.refreshActive();
        },
        (error) => {
          console.error(error);
        }
      );
    },
    stop() {
      if (this.description == null || this.description == "") {
        alert("Description required to log record.");
        return;
      }
      timesheetService.completeActiveTimesheet(this.description).then(
        () => {
          clearInterval(this.timer);
          this.elapsedTime = 0;
          this.$store.commit("SET_ACTIVE_TIMESHEET", null);
          this.getTimesheets();
        },
        (error) => {
          console.error(error);
        }
      );
    },
    getTimesheets() {
      timesheetService.getAllTimesheets().then((response) => {
        this.$store.commit("SET_TIMESHEETS", response.data);
      });
    },
    refreshActive() {
      this.loadingTimesheet = true;
      timesheetService.getActiveTimesheet().then(
        (response) => {
          this.loadingTimesheet = false;
          if (response.data != null && response.data != "") {
            this.$store.commit("SET_ACTIVE_TIMESHEET", response.data);
            let startTime = Date.parse(response.data.beginningTime);
            let currentTime = new Date().getTime();
            this.elapsedTime = currentTime - startTime;

            this.timer = setInterval(() => {
              this.elapsedTime += 1000;
            }, 1000);
          } else {
            this.$store.commit("SET_ACTIVE_TIMESHEET", null);
          }
        },
        (error) => {
          // TODO: handle better
          console.error(error);
        }
      );
    },
  },
  created() {
    ProjectService.getAllProjects().then((response) => {
      this.$store.commit("SET_PROJECTS", response.data);
    });
    this.refreshActive();
  },
};
</script>

<style scoped>
h3 {
  margin: 0;
  background-color: #363636;
  text-align: center;
  padding: 1rem;
  color: #d6d6d6;
}

.stop-watch {
  position: fixed;
  bottom: 0px;
  padding: 0;
  right: 2rem;
  width: 325px;
  box-shadow: 0 0 5px 2px rgb(221, 221, 221);
}

.stop-watch > div {
  padding: 0.5rem 1.5rem 1.5rem 1.5rem;
}

p {
  text-align: center;
  font-weight: bold;
  font-size: 1.5rem;
}

.footer-row p:first-child {
  font-size: 1.15rem;
}

label {
  padding: 0;
}

.btn-success {
  background-color: #a3e000;
  color: #1a2400;
  font-weight: bold;
  border: none;
}

.btn-success:hover {
  background-color: #c1f531;
  transition: all 0.25s ease;
  color: #1a2400;
}

.btn-danger {
  background-color: #c74513;
}

.btn-danger:hover {
  background-color: rgb(252, 116, 62);
  border: none;
}
.minimized div {
  display: none;
}
#title-time:hover {
  background-color: #6b6767;
}
</style>

     