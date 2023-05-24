<template>
    <div>
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
                <v-col cols="2">
                    <v-label>너의 이름은?</v-label>
                </v-col>
                <v-col cols="6">
                    <v-text-field v-model="name" v-on:keyup.enter="setName" label="이름 ..."></v-text-field>
                </v-col>
                <v-col cols="4">
                    <v-btn v-on:click="setName">Send</v-btn>
                </v-col>
            </v-row>
        </v-container>
        <v-container>
            <v-row>
                <v-table v-show="conversation"
                         fixed-header
                         height="300px"
                >
                    <thead>
                    <tr>
                        <th>Greetings</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="greeting in greetings" v-bind:key="greeting">
                        <td>{{ greeting.content }}</td>
                    </tr>
                    </tbody>
                </v-table>
            </v-row>
        </v-container>
    </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SocketJS from 'sockjs-client'

export default {
    name: "GreetingHome",
    props: {},
    data: () => {
        return {
            connected: false,
            conversation: false,
            stompClient: null,
            greetings: [],
            name: null,
        }
    },
    methods: {
        setConnected(connected) {
            this.connected = connected
            this.conversation = connected
            this.greetings = []
        },
        connect() {
            let socket = new SocketJS("http://localhost:8080/ws")
            this.stompClient = Stomp.over(socket);
            this.stompClient.connect({}, (frame) => {
                this.setConnected(true);
                console.log("Connected: " + frame);
                this.stompClient.subscribe("/topic/greetings", (greeting) => {
                    this.showGreeting(JSON.parse(greeting.body))
                });
            })
        },
        disconnect() {
            if (this.stompClient !== null) {
                this.stompClient.disconnect();
            }
            this.setConnected(false);
            console.log("Disconnected")

        },
        showGreeting(message) {
            this.greetings.push(message)
        },
        setName() {
            if (this.stompClient == null) {
                alert("WebSocket 연결을 먼저 하세요.")
            } else {
                this.stompClient.send("/app/hello", JSON.stringify({'name': this.name}), {})
                this.name = null
            }
        },
    },
}
</script>

<style scoped>

</style>