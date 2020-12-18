<template>
  <body>
    <div class="table-wrapper" v-if="$store.state.report.length > 0">
      <div class="table-title">
        <div class="row">
          <div class="col-sm-6">
            <div id="title">Your Report</div>
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
            <th>Time Worked</th>
          </tr>
        </thead>
        <tbody>
     <tr v-for="timesheet in $store.state.report" v-bind:key="timesheet.id">
          <td v-if="timesheet.endingTime != null">
            <img v-if="getUserImage(timesheet.userID) != ''"
                  :src="getUserImage(timesheet.userID)"
                />
                <img v-if="getUserImage(timesheet.userID) === ''"
                  src="img\kronos-logo.png"
                />
          </td>

          <td v-if="timesheet.endingTime != null"> 
            <span>
              {{ timesheet.projectName }}
            </span>
          </td>
          <td v-if="timesheet.endingTime != null">
            <span>{{ timesheet.description }}</span>
          </td>
               <td v-if="timesheet.endingTime != null">
            <span>{{(new Date(timesheet.beginningTime)).toDateString()}}</span> 
            <br>
            <span>{{(new Date(timesheet.beginningTime)).toLocaleTimeString()}} </span>
          </td>
           <td v-if="timesheet.endingTime != null">
            <span >{{ (new Date(timesheet.endingTime)).toDateString()}}</span>
            <br>
             <span>{{(new Date(timesheet.endingTime)).toLocaleTimeString()}} </span>
          </td>
           <span>{{
              secondsToHms(
                getDifferenceInTimes(
                  timesheet.beginningTime,
                  timesheet.endingTime
                )
              )
            }}</span>
        </tr>
  
        </tbody>
        <tfoot>
          <tr>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col">{{ reduce() }}</th>
          </tr>
        </tfoot>
      </table>
    </div>
  </body>
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
  computed: {},
  methods: {
    getDifferenceInTimes(beg, end) {
      if (end == null) {
        return 0;
      }
      let dateBeg = new Date(beg);
      let dateEnd = new Date(end);
      return (dateEnd.getTime() - dateBeg.getTime()) / 1000;
    },
    secondsToHms(d) {
      d = Number(d);
      var h = Math.floor(d / 3600);
      var m = Math.floor((d % 3600) / 60);
      var s = Math.floor((d % 3600) % 60);
      var hDisplay = h > 0 ? h + (h == 1 ? " hour " : " hours ") : "";
      var mDisplay = m > 0 ? m + (m == 1 ? " minute " : " minutes ") : "";
      var sDisplay = s > 0 ? s + (s == 1 ? " second" : " seconds") : "";
      return hDisplay + mDisplay + sDisplay;
    },
    reduce() {
      let total = this.$store.state.report.reduce((a, b) => {
        return a + this.getDifferenceInTimes(b.beginningTime, b.endingTime);
      }, 0);
      return this.secondsToHms(total);
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
    timesheetService.getAllTimesheets().then((response) => {
      this.$store.commit("SET_TIMESHEETS", response.data);
    });
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
    border-bottom: 1px solid #A3E000;
    padding-bottom: 15px;
    background-color: #363636;
    margin: -20px -31px 10px;
    padding: 15px 30px;
    color: #fff;
}

table.table tr th, table.table tr td {
    border-color: #E9E9E9;
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
    background-color: #FCFCFC;
}
#trash {
  color: #C74513;
}
#title {
  font-size: 25px;
}

img {
  width: 50px;
  border-radius: 50%;
}
</style>