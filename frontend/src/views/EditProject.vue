<template>
  <div>
      <h1>Edit Project</h1>
      <update-project v-bind:projectID="parseInt($route.params.id)"/>
  </div>
</template>

<script>
import UpdateProject from '../components/UpdateProject.vue'


export default {
  components: { 
    UpdateProject 
    },

}
</script>

<style>

</style>