export function splitThousands(val, replaceValue = ' ') {
    if (isNaN(val)) {
        return val;
    }

    val = Math.floor(Number(val));
    const prefix = val < 0 ? '-' : '';

    return prefix + val
        .toString()
        .replace(/\D/g, '')
        .replace(/\B(?=(\d{3})+(?!\d))/g, replaceValue);
}
