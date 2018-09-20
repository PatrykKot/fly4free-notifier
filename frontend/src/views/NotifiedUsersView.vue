<template>
    <div>
        <v-container grid-list-xs text-xs-center>
            <v-layout row wrap>
                <v-flex xs12 :key="index" v-for="(notifiedUser, index) in notifiedUsers">
                    <notified-user-card :notified-user="notifiedUser"
                                        :site-types="siteTypes"
                                        @delete="deleteUser(notifiedUser)"
                                        @change="notifiedUserChanged(index, $event)"/>
                </v-flex>
            </v-layout>
        </v-container>
        <v-btn @click="addNew">Dodaj nowy adres</v-btn>
        <v-btn @click="save">Zapisz</v-btn>
        <v-snackbar v-model="snackBar"
                    :color="snackBarColor"
                    :timeout="1500">
            {{snackBarText}}
        </v-snackbar>
    </div>
</template>

<script>
    import SiteTypeService from "../service/SiteTypeService";
    import {addAll, clearArray} from "../util/arrayUtils";
    import NotifiedUsersService from "../service/NotifiedUsersService";
    import NotifiedUserCard from "../components/NotifiedUserCard";
    import VLabel from "vuetify/es5/components/VLabel";

    export default {
        name: "NotifiedUsersView",
        components: {NotifiedUserCard, VLabel},

        data: () => ({
            siteTypes: [],
            notifiedUsers: [],
            loaded: false,
            snackBar: false,
            snackBarText: '',
            snackBarSuccess: true
        }),

        created() {
            this.reload()
        },

        computed: {
            snackBarColor() {
                if (this.snackBarSuccess) {
                    return 'green'
                }
                else {
                    return 'red'
                }
            }
        },

        methods: {
            reload() {
                this.loaded = false

                SiteTypeService.getAll()
                    .then(result => {
                        clearArray(this.siteTypes)
                        addAll(this.siteTypes, result)
                    })
                    .then(() => NotifiedUsersService.getAll())
                    .then(result => {
                        clearArray(this.notifiedUsers)
                        addAll(this.notifiedUsers, result)
                    })
                    .then(() => this.loaded = true)
            },

            addNew() {
                this.notifiedUsers.push({
                    email: '',
                    phrases: []
                })
            },

            deleteUser(notifiedUser) {
                let index = this.notifiedUsers.indexOf(notifiedUser)
                this.notifiedUsers.splice(index, 1)
            },

            notifiedUserChanged(index, newNotifiedUser) {
                this.$set(this.notifiedUsers, index, newNotifiedUser)
            },

            save() {
                let notifiedUsersDto = this.notifiedUsers.map(item => ({
                    email: item.email,
                    phrases: item.phrases.map(phrase => ({
                        text: phrase.text,
                        siteType: phrase.siteType
                    }))
                }))

                NotifiedUsersService.updateAll(notifiedUsersDto)
                    .then(() => {
                        this.snackBarText = 'Zapisano pomyślnie'
                        this.snackBar = true
                        this.snackBarSuccess = true

                        this.reload()
                    })
                    .catch(() => {
                        this.snackBarText = 'Wystąpił błąd poczas zapisywania'
                        this.snackBar = true
                        this.snackBarSuccess = false
                    })
            }
        }
    }
</script>