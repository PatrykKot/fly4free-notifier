const OFFERS = {
    text: 'Oferty',
    icon: 'local_offer',
    link: ''
}

const NOTIFICATIONS = {
    text: 'Powiadomienia',
    icon: 'notifications',
    link: 'notifications'
}

const SETTINGS = {
    text: "Ustawienia",
    icon: "settings",
    link: 'settings'
}

module.exports = {
    OFFERS, NOTIFICATIONS, SETTINGS,

    getPageTypes() {
        return [OFFERS, NOTIFICATIONS, SETTINGS]
    }
}