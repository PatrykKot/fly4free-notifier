<template>
    <v-card flat>
        <v-card-text>
            <v-data-table
                    :headers="headers"
                    :items="events">
                <template slot="items" slot-scope="props">
                    <td>
                        <v-tooltip bottom>
                            <span slot="activator"
                                  :style="{cursor: props.item.link ? 'pointer' : 'default'}"
                                  @click="onEventClick(props.item)">
                            {{ props.item.normalizedContent }}
                            </span>
                            <span>
                                {{props.item.innerTitle}}
                            </span>
                        </v-tooltip>
                    </td>
                    <td>
                        <template v-if="props.item.siteByPhrases.length > 0">
                            <v-tooltip bottom>
                            <span slot="activator"
                                  class="event-by-phrase-count">
                                {{ props.item.siteByPhrases.length }}
                            </span>
                                <v-list>
                                    <template v-for="siteByPhrase in props.item.siteByPhrases">
                                        <v-subheader>
                                            Email: <b>{{siteByPhrase.email}}</b>
                                        </v-subheader>
                                        <v-subheader>
                                            Fraza: <b>{{siteByPhrase.phraseText}}</b>
                                        </v-subheader>
                                        <v-subheader>
                                            <span>Wysłano maila:</span>
                                            <span v-if="siteByPhrase.emailSent"
                                                  style="color: green">
                                                <b>TAK</b>
                                            </span>
                                            <span v-else
                                                  style="color: red">
                                                <b>NIE</b>
                                            </span>
                                        </v-subheader>
                                    </template>
                                </v-list>
                            </v-tooltip>
                        </template>
                        <template v-else>
                            <span slot="activator"
                                  class="event-by-phrase-count"
                                  style="cursor: default">
                                {{ props.item.siteByPhrases.length }}
                            </span>
                        </template>
                    </td>
                    <td>{{ props.item.siteType.text }}</td>
                    <td>{{ formatDate(props.item.date) }}</td>
                </template>
            </v-data-table>
        </v-card-text>
        <v-card-actions>
            <v-btn @click="deleteAll">Usuń wszystkie</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import SiteEventService from '../service/SiteEventService'
    import {addAll, clearArray} from "../util/arrayUtils";
    import moment from 'moment'

    export default {
        name: "SiteEventsView",
        data: () => ({
            events: [],

            headers: [
                {text: 'Treść', value: 'normalizedContent'},
                {text: 'Pasujących fraz', value: 'siteByPhrases.length'},
                {text: 'Strona', value: 'siteType'},
                {text: 'Data', value: 'date'}
            ]
        }),

        created() {
            this.reload()
        },

        methods: {
            reload() {
                SiteEventService.getAll()
                    .then(result => {
                        clearArray(this.events)
                        addAll(this.events, result)
                    })
            },

            formatDate(time) {
                return moment(time).format('YYYY-MM-DD HH:mm:ss')
            },

            onEventClick(event) {
                if (event.link) {
                    this.openInNewTab(event.link)
                }
            },

            openInNewTab(url) {
                let win = window.open(url, '_blank')
                win.focus()
            },

            deleteAll() {
                SiteEventService.deleteAll()
                    .then(() => this.reload())
            }
        }
    }
</script>

<style scoped>
    .event-by-phrase-count {
        cursor: pointer;
        font-size: 20px;
    }
</style>