<template>
    <v-card flat>
        <v-card-text>
            <v-data-table
                    :headers="headers"
                    :items="events">
                <template slot="items" slot-scope="props">
                    <td>{{ props.item.content }}</td>
                    <td>{{ props.item.siteType.text }}</td>
                    <td>{{ formatDate(props.item.date) }}</td>
                </template>
            </v-data-table>
        </v-card-text>
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
                {text: 'Treść', value: 'content'},
                {text: 'Strona', value: 'siteType'},
                {text: 'Data', value: 'date'}
            ]
        }),

        created() {
            SiteEventService.getAll()
                .then(result => {
                    clearArray(this.events)
                    addAll(this.events, result)
                })
        },

        methods: {
            formatDate(time) {
                return moment(time).format('YYYY-MM-DD HH:mm:ss')
            }
        }
    }
</script>