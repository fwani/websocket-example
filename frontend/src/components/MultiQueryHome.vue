<template>
    <div>
        <h1>MultiQueryHome</h1>
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
        <v-container>
            <v-row>
                <v-col v-for="idx in 3" :key="idx">
                    <QueryBody ref="queryBody2" :stompClient="stompClient"/>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
import QueryBody from "@/components/QueryBody.vue";
import SocketJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
    name: "MultiQueryHome",
    components: {QueryBody},
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
                    this.$refs.queryBody2.forEach(x=> {x.showResult(jsonTree)})
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