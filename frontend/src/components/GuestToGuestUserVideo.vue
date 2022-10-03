<template>
<div v-if="streamManager">
	<div>
		<ov-video :stream-manager="streamManager"/>
		<p style="marign-bottom:1rem">{{ clientData }}</p>
	</div>
</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'GuestToGuestUserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>

<style>
	video {
		width: 100%;
		height: 100%;
		margin-bottom: 0rem;
	}
</style>
