<template>
    <single-card class="card">
        <user-details />
    </single-card>
</template>

<script>
import UserDetails from '../components/UserDetails.vue'
import SingleCard from '@/components/SingleCard.vue'

export default {
    components: {
        UserDetails,
        SingleCard
    }
}
</script>

<style>

</style>