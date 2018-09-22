<template>
    <v-app>
        <v-content>
            <v-container fluid class="main-container">
                <v-tabs v-model="tab"
                        color="cyan"
                        dark
                        slider-color="yellow"
                        grow>
                    <v-tab>
                        Zdarzenia
                    </v-tab>
                    <v-tab-item>
                        <site-events-view ref="siteEventsView"/>
                    </v-tab-item>
                    <v-tab>
                        Powiadomienia
                    </v-tab>
                    <v-tab-item>
                        <notified-users-view
                                @changed="notifiedUsersChanged"/>
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
        }),

        methods: {
            notifiedUsersChanged() {
                this.$refs.siteEventsView.reload()
            }
        }
    }
</script>

<style scoped>
    .main-container {
        padding: 0;
    }
</style>