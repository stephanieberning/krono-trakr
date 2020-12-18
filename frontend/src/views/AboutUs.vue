<template>
  <about-us :team-members="teamMembers" />
</template>

<script>
import AboutUs from "../components/AboutUs.vue";

export default {
  components: { AboutUs },
  data() {
    return {
      teamMembers: [
        {
          name: "Jared Bussell",
          description: `Hey there, my name is Jared Bussell and I’m currently a Software Engineer 
          in the Cincinnati Area. During the summer of 2020 I did some free online coding 
          programs and developed a passion for it ever since so I decided to enroll in Tech Elevator 
          and take the Java cohort in order to persue a career in Tech. I have always enjoyed logic 
          based puzzles and working on any type of project from home restoration all the way to 
          fixing and working with computers.`,
          linkedIn: "jared-bussell",
          image: "/img/jared.jpg",
        },
        {
          name: "Brittany Blankenship",
          description: `Hi, my name is Brittany and I make art. I really enjoy that 
          the technology space really allows for individuals to express their 
          creative side and that’s why I switched to tech after holding several 
          retail-management positions. After my graduation at Tech Elevator, I look forward 
          to making apps and websites that add to the user experience with a company that 
          cultivates creativity and innovation.`,
          linkedIn: "brittanyblankenship",
          image: "/img/brittany.jpg",
        },
        {
          name: "Ross Mather",
          description: `Before Tech Elevator I was living and working in Saint-Petersburg,
            Russia. I took an introductory programming course while in Russia
            and was immediately hooked. I quit my job and moved back to America,
            to pursue a career in Tech.`,
          linkedIn: "ross-mather",
          image: "/img/Ross.png",
        },
        {
          name: "Winnie Kimani",
          description: `My passion is to provide quality service led by integrity at all
            time. With the exposure to data analysis and quality improvement
            dealing with performance improvement tools. This is what peaked my
            interest to fully immerse myself in the tech field. My goal is to
            apply the knowledge and skills I have gained to a company that
            fosters growth and provides challenging opportunities.`,
          linkedIn: "winnie-kimani",
          image: "/img/winnie.jpg",
        },
        {
          name: "Stephanie Berning",
          description: `Hi! I'm a software developer from Cincinnati, OH. I recently
            completed the Java bootcamp at Tech Elevator. My background includes
            customer service and clerical work at the Hamilton County
            Courthouse, working with lawyers, courtroom staff, and defendants.
            Working at the courthouse helped me gain a wealth of experience with
            critical thinking and data analysis. I hope to continue
            to improve my coding skills through group collaboration adn add
            value to where I work.`,
          linkedIn: "stephanieaberning",
          image: "/img/Stephanie.png",
        },
      ],
    };
  },
};
</script>

<style>
</style>