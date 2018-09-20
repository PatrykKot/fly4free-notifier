export function clearArray(array) {
    array.splice(0, array.length)
}

export function addAll(array, newItems) {
    newItems.forEach(item => array.push(item))
}