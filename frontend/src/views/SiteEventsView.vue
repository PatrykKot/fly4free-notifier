<template>
    <v-card flat>
        <v-card-text>
            <v-data-table
                    :headers="headers"
                    :items="events"
                    :total-items="total"
                    :loading="loading"
                    :pagination.sync="pagination"
                    no-data-text="Brak wyników"
                    rows-per-page-text="Liczba wyników na stronie"
                    :rows-per-page-items="[5,10,25,{text: 'Wszystkie', value: null}]"
                    disable-initial-sort>
                <template slot="headerCell" slot-scope="props">
                    <span v-if="!props.header.search">
                        {{props.header.text}}
                    </span>
                    <v-text-field v-else
                                  v-model="search"
                                  :label="props.header.text"
                                  prepend-icon="search"/>
                </template>
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
                <template slot="pageText" slot-scope="props">
                    <span v-if="events.length > 0">
                        Wyniki {{ props.pageStart }} - {{ props.pageStop }} z {{ props.itemsLength }}
                    </span>
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
    import SiteByPhrasesElement from "../components/SiteByPhrasesElement";
    import VTextField from "vuetify/es5/components/VTextField";
    import timeago from 'timeago.js';

    export default {
        name: "SiteEventsView",
        components: {VTextField, SiteByPhrasesElement},

        data: () => ({
            search: '',
            searchTimeout: null,
            events: [],
            headers: [
                {text: 'Treść', value: 'normalizedContent', search: true, sortable: false},
                {text: 'Pasujących fraz', value: 'siteByPhrases.length', sortable: false},
                {text: 'Strona', value: 'siteType'},
                {text: 'Data', value: 'date'}
            ],
            total: 0,
            loading: true,
            pagination: {
                sortBy: 'date',
                descending: true
            }
        }),

        watch: {
            pagination: {
                handler() {
                    this.reload()
                },
                deep: true
            },
            search() {
                let me = this

                clearTimeout(me.searchTimeout)
                me.searchTimeout = setTimeout(() => me.reload(), 500)
            }
        },

        methods: {
            reload() {
                this.loading = true
                const {sortBy, descending, page, rowsPerPage} = this.pagination

                SiteEventService.getEvents(sortBy, descending, page, rowsPerPage, this.search.trim())
                    .then(result => {
                        clearArray(this.events)
                        addAll(this.events, result.data)
                        this.total = result.count
                        this.loading = false
                    })
            },

            formatDate(time) {
                return timeago().format(new Date(time), 'pl')
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