<template>
    <v-app>
        <v-content>
            <v-container fluid class="main-container">
                <v-tabs v-model="tab"
                        color="cyan"
                        dark
                        slider-color="yellow">
                    <v-tab ripple>
                        Zdarzenia
                    </v-tab>
                    <v-tab-item>
                        <site-events-view/>
                    </v-tab-item>
                    <v-tab ripple>
                        Powiadomienia
                    </v-tab>
                    <v-tab-item>
                        <notified-users-view/>
                    </v-tab-item>
                </v-tabs>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import NotifiedUsersView from "./views/NotifiedUsersView";
    import SiteEventsView from "./views/SiteEventsView";

    export default {
        components: {SiteEventsView, NotifiedUsersView},
        data: () => ({
            tab: 0
        })
    }
</script>

<style scoped>
    .main-container {
        padding: 0
    }
</style>