<template>
    <v-list dense>
        <v-list-tile v-for="(pageType, index) in pages"
                     :key="index"
                     @click="linkClicked(pageType)">
            <v-list-tile-action>
                <v-icon>{{pageType.icon}}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
                <v-list-tile-title>
                    {{pageType.text}}
                </v-list-tile-title>
            </v-list-tile-content>
        </v-list-tile>
    </v-list>
</template>

<script>
    import {getPageTypes} from "../util/pageTypes";

    export default {
        name: "DrawerContent",

        computed: {
            pages() {
                return getPageTypes()
            }
        },

        methods: {
            linkClicked(pageType) {
                this.$emit('pageChanged', pageType)
            }
        }
    }
</script>