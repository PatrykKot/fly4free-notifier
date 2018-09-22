<template>
    <v-card flat>
        <v-card-text>
            <v-data-table
                    :headers="headers"
                    :items="events"
                    disable-initial-sort>
                <template slot="items" slot-scope="props">
                    <td>
                        <span :style="{cursor: props.item.link ? 'pointer' : 'default'}"
                              @click="onEventClick(props.item)">
                            {{ props.item.normalizedContent }}
                        </span>
                    </td>
                    <td>
                        <site-by-phrases-element :site-by-phrases="props.item.siteByPhrases"/>
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
    import SiteByPhrasesElement from "../components/SiteByPhrasesElement";

    export default {
        name: "SiteEventsView",
        components: {SiteByPhrasesElement},
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