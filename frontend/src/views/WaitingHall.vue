<template>
	<waiting-company v-if="!state.isGuest"></waiting-company>
	<waiting-guest v-if="state.isGuest"></waiting-guest>
</template>

<script>
import WaitingGuest from '@/components/WaitingGuest.vue'
import WaitingCompany from '@/components/WaitingCompany.vue'
import { useStore } from 'vuex'
import { computed, reactive } from 'vue'

export default {
	name: 'waiting-hall',

	components: {
		WaitingGuest,
		WaitingCompany
	},
	setup() {
		const store = useStore()
		const state = reactive({
			isGuest: computed(() => store.getters['root/getIsGuest'])
		})
		return { state }
	}

}
</script>

<style scoped>
  .container{
    height: 1000px;
    background: #f0ebe7;
  }
</style>
