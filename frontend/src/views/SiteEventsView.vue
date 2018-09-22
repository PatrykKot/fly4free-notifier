<template>
    <v-card flat>
        <v-card-text>
            <v-data-table
                    :headers="headers"
                    :items="events"
                    :total-items="total"
                    :loading="loading"
                    :pagination.sync="pagination"
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
                {text: 'Treść', value: 'normalizedContent', sortable: false},
                {text: 'Pasujących fraz', value: 'siteByPhrases.length', sortable: false},
                {text: 'Strona', value: 'siteType', sortable: false},
                {text: 'Data', value: 'date'}
            ],
            total: 0,
            loading: true,
            pagination: {}
        }),

        watch: {
            pagination: {
                handler() {
                    this.reload()
                },
                deep: true
            }
        },

        methods: {
            reload() {
                this.loading = true
                const {sortBy, descending, page, rowsPerPage} = this.pagination

                SiteEventService.getEvents(sortBy, descending, page, rowsPerPage)
                    .then(result => {
                        clearArray(this.events)
                        addAll(this.events, result.data)
                        this.total = result.count
                        this.loading = false
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