<template>
    <div>
        <v-container>
            <v-row>
                <v-col cols="10">
                    <v-textarea v-model="query" label="DSL Query"></v-textarea>
                </v-col>
                <v-col>
                    <v-btn @click="sendQuery">send</v-btn>
                </v-col>
            </v-row>
        </v-container>
        <v-container>
            <v-table fixed-header height="400px">
                <thead>
                <tr>
                    <th v-for="field in fields" :key="field">{{ field.name }} / {{ field.type }}</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="record in records" :key="record">
                    <td v-for="data in record" :key="data">{{ data }}</td>
                </tr>
                </tbody>
            </v-table>
        </v-container>
    </div>
</template>

<script>
import Client from 'webstomp-client'
import axios from 'axios'

export default {
    name: "QueryBody",
    props: {
        stompClient: Client,
    },
    data: () => {
        return {
            query: "",
            jobId: null,
            total: 0,
            current: 0,
            fields: [
                {'name': 'colA', 'type': 'TEXT'},
                {'name': 'colB', 'type': 'INTEGER'}
            ],
            records: [
                ['hi', 1],
                ['hello', 2],
                ['fwani', 3],
                ['fwani', 3],
                ['fwani', 3],
                ['fwani', 3],
                ['fwani', 3],
                ['fwani', 3],
                ['fwani', 3],
            ]
        }
    },
    methods: {
        sendQuery() {
            axios.post('http://localhost:8080/api/query', JSON.stringify({'q': this.query}), {
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then((result) => {
                    this.jobId = result.data.jobId;
                    console.log("JobId: " + this.jobId)
                    this.getResult()
                })
        },
        getResult() {
            if (this.stompClient == null) {
                alert("WebSocket 연결을 먼저 하세요.")
            } else {
                this.stompClient.send("/app/query", JSON.stringify({'jobId': this.jobId}), {});
            }
        },
        showResult(result) {
            if (result.jobId === this.jobId){
                this.total = result.total
                this.current = result.current
                this.fields = result.fields
                this.records = result.records
            }
        },
    }
}
</script>

<style scoped>

</style>