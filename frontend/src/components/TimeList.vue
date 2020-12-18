<template>
  <div class="table-wrapper">
    <div class="table-title">
      <div class="row">
        <div class="col-sm-6">
          <div id="title">Logged Time</div>
        </div>
      </div>
    </div>
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th>User Image</th>
          <th>Project Name</th>
          <th>Description</th>
          <th>Beginning Time</th>
          <th>Ending Time</th>
          <th>Delete</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="timesheet in $store.state.timesheets"
          v-bind:key="timesheet.id"
        >
        <td>
            <img v-if="getUserImage(timesheet.userID) != ''"
                  :src="getUserImage(timesheet.userID)"
                />
                <img v-if="getUserImage(timesheet.userID) === ''"
                  src="img\kronos-logo.png"
                />
          </td>
          <td>
            <span>
              {{ timesheet.projectName }}
            </span>
          </td>
          <td>
            <span>{{ timesheet.description }}</span>
          </td>
          <td>
            <span>{{ new Date(timesheet.beginningTime).toDateString() }}</span>
            <br />
            <span
              >{{ new Date(timesheet.beginningTime).toLocaleTimeString() }}
            </span>
          </td>
          <td>
            <span v-if="timesheet.endingTime != null">{{
              new Date(timesheet.endingTime).toDateString()
            }}</span>
            <span v-else>Time is being recorded</span>
            <br />
            <span v-if="timesheet.endingTime != null"
              >{{ new Date(timesheet.endingTime).toLocaleTimeString() }}
            </span>
          </td>

          <td>
            <a href="#" v-on:click="deleteTimesheet(timesheet.timeID)"
              ><font-awesome-icon icon="trash" id="trash"
            /></a>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

import timesheetService from "@/services/TimesheetService.js";


export default {
  name: "time-list",
  data() {
    return {
      timesheet: {
        timeID: "",
        projectID: "",
        projectName: "",
        userID: "",
        description: "",
        beginningTime: "",
        endingTime: "",
      },
    };
  },
  methods: {
    getTimesheets() {
      timesheetService.getAllTimesheets().then((response) => {
        this.$store.commit("SET_TIMESHEETS", response.data);
      });
    },
    deleteTimesheet(timeID) {
      if (
        confirm(
          "Are you sure you want to delete this timesheet? This action cannot be undone."
        )
      )
        timesheetService
          .deleteTimesheet(timeID)
          .then((response) => {
            {
              if (response.status === 200) {
                this.getTimesheets();
              }
            }
          })
          .catch((error) => {
            if (error.response) {
              return (
                "Failed to update timesheet. Response was: " +
                error.response.data.message
              );
            } else if (error.request) {
              return "Failed to connect to server.";
            } else {
              return "Something went really wrong.";
            }
          });
    },
    getUserImage(id) {
      let users = this.$store.state.employees;
      // users.push(this.$store.state.user);
      let u = users.find(element => element.id == id);
      // console.log('looking for ' + id);
      // console.log(users);
      // console.log('found ' + u);
      if (u) {
        
        return u.avatar;
      } else {
        if (id == this.$store.state.user.id) {
          return this.$store.state.user.avatar;
        } else {
          return '';
        }
        
              }
      
    }
  },
  created() {
    this.getTimesheets();
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
  border-bottom: 1px solid #a3e000;
  padding-bottom: 15px;
  background-color: #363636;
  margin: -20px -31px 10px;
  padding: 15px 30px;
  color: #fff;
}
.table-title h2 {
  margin: 2px 0 0;
  font-size: 24px;
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

#trash {
  color: #c74513;
}

#title {
  font-size: 25px;
}

img {
  width: 50px;
  border-radius: 50%;
}
</style>