<template>
    <v-app>
        <v-main>
            <v-card>
                <v-tabs v-model="tab">
                    <v-tab v-for="item in items" :key="item.name">{{ item.name }}</v-tab>
                </v-tabs>
                <v-window v-model="tab">
                    <v-window-item v-for="item in items" :key="item.name">
                        <component :is="item.component"></component>
                    </v-window-item>
                </v-window>
            </v-card>
        </v-main>
    </v-app>
</template>

<script>
import GreetingHome from "@/components/GreetingHome.vue";
import QueryHome from "@/components/QueryHome.vue";
import {markRaw} from "vue";
import MultiQueryHome from "@/components/MultiQueryHome.vue";

export default {
    name: 'App',

    components: {},

    data: () => ({
        tab: null,
        items: [
            {"name": "greeting", "component": markRaw(GreetingHome)},
            {"name": "query", "component": markRaw(QueryHome)},
            {"name": "multi-query", "component": markRaw(MultiQueryHome)}
        ],
    }),
    methods: {}
}
</script>
