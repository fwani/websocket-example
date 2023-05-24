<template>
    <div>
        <h1>QueryHome</h1>
        <v-container>
            <v-row>
                <v-col>
                    <v-label>WebSocket connection:</v-label>
                </v-col>
                <v-col>
                    <v-btn v-on:click="connect" :disabled="connected">Connect</v-btn>
                    <v-btn v-on:click="disconnect" :disabled="!connected">Disconnect</v-btn>
                </v-col>
            </v-row>
        </v-container>
        <QueryBody ref="queryBody" :stompClient="stompClient"/>
    </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SocketJS from 'sockjs-client'
import QueryBody from "@/components/QueryBody.vue";

export default {
    name: "QueryHome",
    components: {QueryBody},
    props: {},
    data: () => {
        return {
            connected: false,
            conversation: false,
            stompClient: null,
        }
    },
    methods: {
        setConnected(connected) {
            this.connected = connected
            this.conversation = connected
        },
        connect() {
            let socket = new SocketJS("http://localhost:8080/ws")
            this.stompClient = Stomp.over(socket);
            this.stompClient.connect({}, (frame) => {
                this.setConnected(true);
                console.log("Connected: " + frame);
                this.stompClient.subscribe("/topic/query", (result) => {
                    const jsonTree = JSON.parse(result.body)
                    this.$refs.queryBody.showResult(jsonTree)
                });
            })
        },
        disconnect() {
            if (this.stompClient !== null) {
                this.stompClient.disconnect();
                this.stompClient = null
            }
            this.setConnected(false);
            console.log("Disconnected")

        },
    }
}
</script>

<style scoped>

</style>