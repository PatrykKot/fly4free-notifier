<template>
    <v-expansion-panel>
        <v-expansion-panel-content>
            <div slot="header">
                <span v-if="!!email">{{email}}</span>
                <span v-else><i>Nowy adres email</i></span>
            </div>
            <v-card>
                <v-card-text>
                    <v-form>
                        <v-text-field v-model="email"
                                      label="Email"/>
                    </v-form>

                    <phrase-component :key="index"
                                      v-for="(phrase, index) in phrases"
                                      :phrase="phrase"
                                      :site-types="siteTypes"
                                      @change="phraseChanged(index, $event)"
                                      @delete="deletePhrase(index)"/>

                    <v-btn @click="addPhrase">Nowa fraza</v-btn>
                    <v-btn @click="$emit('delete')">Usuń adres</v-btn>
                </v-card-text>
            </v-card>
        </v-expansion-panel-content>
    </v-expansion-panel>
</template>

<script>
    import VTextField from "vuetify/es5/components/VTextField";
    import VSelect from "vuetify/es5/components/VSelect";
    import {addAll} from "../util/arrayUtils";
    import VLabel from "vuetify/es5/components/VLabel";
    import PhraseComponent from "./PhraseComponent";

    export default {
        name: "NotifiedUserCard",

        components: {PhraseComponent, VLabel, VSelect, VTextField},

        props: ['notifiedUser', 'siteTypes'],

        data: () => ({
            email: '',
            phrases: []
        }),

        watch: {
            email() {
                this.callChange()
            },

            phrases() {
                this.callChange()
            }
        },

        created() {
            this.email = this.notifiedUser.email
            addAll(this.phrases, this.notifiedUser.phrases)
        },

        methods: {
            addPhrase() {
                this.phrases.push({
                    text: '',
                    siteType: null
                })
            },

            phraseChanged(index, newPhrase) {
                this.$set(this.phrases, index, newPhrase)
            },

            deletePhrase(index) {
                this.phrases.splice(index, 1)
            },

            callChange() {
                this.$emit('change', {
                    email: this.email,
                    phrases: this.phrases
                })
            }
        }
    }
</script>