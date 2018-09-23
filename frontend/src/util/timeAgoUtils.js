import timeago from 'timeago.js'

export function initPolishTimeago() {
    let test_local_dict = function (number, index) {
        return [
            ['Przed chwilą', ''],
            ['%s sekund temu', ''],
            ['minutę temu', ''],
            ['%s minut temu', ''],
            ['godzinę temu', ''],
            ['%s godzin temu', ''],
            ['dzień temu', ''],
            ['%s dni temu', ''],
            ['tydzień temu', ''],
            ['%s tygodni temu', ''],
            ['miesiąc temu', ''],
            ['%s miesięcy temu', ''],
            ['rok temu', ''],
            ['%s lat temu', '']
        ][index]
    }

    timeago.register('pl', test_local_dict);
}