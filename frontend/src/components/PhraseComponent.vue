<template>
    <v-layout row wrap>
        <v-flex xs5>
            <v-text-field class="phrase-input"
                          v-model="text"
                          label="Fraza"/>
        </v-flex>
        <v-flex xs5>
            <v-select class="site-select"
                      v-model="siteTypeText"
                      label="Strona"
                      :items="selectItems"/>
        </v-flex>
        <v-flex xs2>
            <div class="delete-outer-container">
                <div class="delete-inner-container">
                    <v-icon @click="$emit('delete')">
                        delete
                    </v-icon>
                </div>
            </div>
        </v-flex>
    </v-layout>
</template>

<script>
    export default {
        name: "PhraseComponent",

        props: ['phrase', 'siteTypes'],

        data: () => ({
            text: '',
            siteTypeText: null
        }),

        watch: {
            text() {
                this.callChange()
            },

            siteTypeText() {
                this.callChange()
            }
        },

        computed: {
            selectItems() {
                return this.siteTypes.map(item => item.text)
            },

            selectedSiteType() {
                if (!!this.siteTypeText) {
                    return this.siteTypes.find(item => item.text == this.siteTypeText).type
                }
                else {
                    return null
                }
            }
        },

        created() {
            this.text = this.phrase.text
            if (this.phrase.siteType) {
                this.siteTypeText = this.siteTypes.find(item => item.type == this.phrase.siteType).text
            }
        },

        methods: {
            callChange() {
                this.$emit('change', {
                    text: this.text,
                    siteType: this.selectedSiteType
                })
            }
        }
    }
</script>

<style scoped>
    @media only screen and (max-width: 500px) {
        .site-select {
            font-size: 11px
        }

        .phrase-input {
            font-size: 11px
        }
    }

    .delete-outer-container {
        display: table;
        height: 100%;
        width: 100%;
    }

    .delete-inner-container {
        display: table-cell;
        vertical-align: middle;
        text-align: center;
    }
</style>