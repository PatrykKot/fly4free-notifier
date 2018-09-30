<template>
    <v-app id="inspire">
        <v-navigation-drawer v-model="drawer"
                             fixed
                             app>
            <drawer-content/>
        </v-navigation-drawer>
        <v-toolbar color="indigo" dark fixed app>
            <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
            <v-toolbar-title>Application</v-toolbar-title>
        </v-toolbar>
        <v-content>
            <v-container fluid fill-height>
                <v-layout justify-center
                          align-center>
                    <v-flex text-xs-center>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import DrawerContent from "./components/DrawerContent";

    export default {
        components: {DrawerContent},
        data: () => ({
            drawer: null
        })
    }
</script>