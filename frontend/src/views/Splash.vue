<template>
  <div class="no-boot">
    <br />
    <div class="full-container">
      <img
        id="mockup"
        src="img\LaptopImage.png"
        alt="Computer Mockup w/ Product"
      />
      <div class="container">
        <h3>Project Management Software Trusted Worldwide</h3>
        <p>
          Quickly log hours to a project by creating your own time logs or by
          using our live time tracking tool. Management can share projects and
          run time reports. Never miss a project deadline again with
          KronoTrakr™.
        </p>
        <router-link to="register" class="btn btn-primary" tag="button"
          >Register an Account</router-link
        >
        <br />
        <router-link to="login" class="btn btn-primary" tag="button"
          >Login</router-link
        >
      </div>
    </div>
    <br />

    <blockquote>
      <p class="quotation">
        I love this software. By switching to KronoTrakr,
        <u>our time reporting has never been more accurate</u>.
      </p>
      <footer>- Bob Ross</footer>
    </blockquote>

    <table class="features-table">
      <tbody>
        <tr>
          <th>
            <img
              src="https://www.flaticon.com/svg/static/icons/svg/833/833593.svg"
              alt="Calendar Pic"
            />
            <br />
            <br />
            <p>
              Create a project and choose to specify an ending date or list as
              'Ongoing'
            </p>
          </th>
          <th>
            <img
              src="https://www.flaticon.com/svg/static/icons/svg/37/37663.svg"
              alt="Clock Pic"
            />
            <br />
            <br />
            <p>
              Log your time to a project you create or a project that has been
              shared with you
            </p>
          </th>
          <th>
            <img
              src="https://www.flaticon.com/svg/static/icons/svg/933/933196.svg"
              alt="Stopwatch Pic"
            />
            <br />
            <br />
            <p>Log your time live with our live tracking tool</p>
          </th>
        </tr>
        <tr>
          <th>
            <img
              src="https://www.flaticon.com/svg/static/icons/svg/1828/1828960.svg"
              alt="Sharing Pic"
            />
            <br />
            <br />
            <p>
              As a manager, share projects with your employees with a click of a
              button
            </p>
          </th>
          <th>
            <img
              src="https://www.flaticon.com/svg/static/icons/svg/2680/2680927.svg"
              alt="Stack Reports Pic"
            />
            <br />
            <br />
            <p>Run reports for just yourself or for your employees</p>
          </th>
          <th>
            <img
              src="https://www.flaticon.com/svg/static/icons/svg/747/747376.svg"
              alt="Person Pic"
            />
            <br />
            <br />
            <p>Customize your own profile</p>
          </th>
        </tr>
      </tbody>
    </table>
    <br />
    <br />
    <blockquote>
      <p class="quotation">
        <u>Excellent and responsive support team</u> for this application.
        Highly recommend!
      </p>
      <footer>- David Draiman</footer>
    </blockquote>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
#mockup {
  width: 700px;
  height: auto;
}
.full-container {
  display: flex;
  flex-flow: row nowrap;
  justify-items: space-between;
  padding: 0px;
  margin: 0px;
}

.container {
  display: flex;
  flex-flow: column nowrap;
  justify-items: center;
  margin: 0px;
  padding: 0px;
}

.quotation {
  font-size: 30px;
  margin: 0 auto;
  quotes: "\201C""\201D""\2018""\2019";
  padding: 10px 20px;
  line-height: 1.4;
}

.quotation:before {
  content: open-quote;
  display: inline;
  height: 0;
  line-height: 0;
  left: -10px;
  position: relative;
  top: 30px;
  color: #ccc;
  font-size: 3em;
}
.quotation::after {
  content: close-quote;
  display: inline;
  height: 0;
  line-height: 0;
  left: 10px;
  position: relative;
  top: 35px;
  color: #ccc;
  font-size: 3em;
}

footer {
  margin: 0;
  text-align: right;
  font-size: 1.5em;
  font-style: italic;
}

img {
  width: 50px;
  height: 50px;
}

.features-table {
  width: 100%;
  text-align: center;
  border-spacing: 20px;
  border-collapse: separate;
  background-image: linear-gradient(#eba24f, #f3f3f3);
  border-radius: 15px;
  table-layout: fixed;
  width: 100%;
}

td {
  width: 25%;
}

</style>