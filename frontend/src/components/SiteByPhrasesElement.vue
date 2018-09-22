<template>
    <div>
        <template v-if="siteByPhrases.length > 0">
            <v-tooltip bottom>
                            <span slot="activator"
                                  class="event-by-phrase-count">
                                {{ siteByPhrases.length }}
                            </span>
                <v-list>
                    <template v-for="siteByPhrase in siteByPhrases">
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
                                {{ siteByPhrases.length }}
                            </span>
        </template>
    </div>
</template>

<script>
    export default {
        name: "SiteByPhrasesElement",

        props: ['siteByPhrases']
    }
</script>


<style scoped>
    .event-by-phrase-count {
        cursor: pointer;
        font-size: 20px;
    }
</style>